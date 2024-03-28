### Deploy the elastic load balancer

In this task you will create a load balancer in AWS that will receive
the HTTP requests from clients and forward them to the Drupal
instances.

![Schema](./img/CLD_AWS_INFA.PNG)

## Task 01 Prerequisites for the ELB

* Create a dedicated security group

|Key|Value|
|:--|:--|
|Name|SG-DEVOPSTEAM13-LB|
|Inbound Rules|Application Load Balancer|
|Outbound Rules|Refer to the infra schema|

```bash
[INPUT]
aws ec2 create-security-group \
    --group-name SG-DEVOPSTEAM13-LB \
    --description "Allow load balancer traffic" \
    --vpc-id vpc-03d46c285a2af77ba \
    --tag-specifications 'ResourceType=security-group,Tags=[{Key=Name,Value=SG-DEVOPSTEAM13-LB}]'


[OUTPUT]
{
    "GroupId": "sg-0a35e6d8e232f46eb",
    "Tags": [
        {
            "Key": "Name",
            "Value": "SG-DEVOPSTEAM13-LB"
        }
    ]
}

[INPUT]
aws ec2 authorize-security-group-ingress \
    --group-id sg-0a35e6d8e232f46eb \
    --ip-permissions IpProtocol=tcp,FromPort=8080,ToPort=8080,IpRanges='[{CidrIp=10.0.0.0/16, Description="Allow HTTP from DMZ"}]' \
    --tag-specifications 'ResourceType=security-group-rule,Tags=[{Key=Name,Value=HTTP-ALLOW}, {Key=Description, Value=Allow HTTP from DMZ}]'

[OUTPUT]
{
    "Return": true,
    "SecurityGroupRules": [
        {
            "SecurityGroupRuleId": "sgr-0e02db3cb73ee574b",
            "GroupId": "sg-0a35e6d8e232f46eb",
            "GroupOwnerId": "709024702237",
            "IsEgress": false,
            "IpProtocol": "tcp",
            "FromPort": 8080,
            "ToPort": 8080,
            "CidrIpv4": "10.0.0.0/16",
            "Description": "Allow HTTP from DMZ",
            "Tags": [
                {
                    "Key": "Description",
                    "Value": "Allow HTTP from DMZ"
                },
                {
                    "Key": "Name",
                    "Value": "HTTP-ALLOW"
                }
            ]
        }
    ]
}
```

* Create the Target Group

|Key|Value|
|:--|:--|
|Target type|Instances|
|Name|TG-DEVOPSTEAM13|
|Protocol and port|Refer to the infra schema|
|Ip Address type|IPv4|
|VPC|Refer to the infra schema|
|Protocol version|HTTP1|
|Health check protocol|HTTP|
|Health check path|/|
|Port|Traffic port|
|Healthy threshold|2 consecutive health check successes|
|Unhealthy threshold|2 consecutive health check failures|
|Timeout|5 seconds|
|Interval|10 seconds|
|Success codes|200|

```bash
[INPUT]
aws elbv2 create-target-group \
    --name TG-DEVOPSTEAM13 \
    --protocol HTTP \
    --protocol-version HTTP1 \
    --port 8080 \
    --vpc-id vpc-03d46c285a2af77ba \
    --health-check-protocol HTTP \
    --health-check-path / \
    --health-check-port 8080 \
    --health-check-interval-seconds 10 \
    --health-check-timeout-seconds 5 \
    --healthy-threshold-count 2 \
    --unhealthy-threshold-count 2 \
    --target-type instance \
    --matcher HttpCode=200 \
    --tags 'Key=Name,Value=TG-DEVOPSTEAM13'

[OUTPUT]
{
    "TargetGroups": [
        {
            "TargetGroupArn": "arn:aws:elasticloadbalancing:eu-west-3:709024702237:targetgroup/TG-DEVOPSTEAM13/71594bed38884946",
            "TargetGroupName": "TG-DEVOPSTEAM13",
            "Protocol": "HTTP",
            "Port": 8080,
            "VpcId": "vpc-03d46c285a2af77ba",
            "HealthCheckProtocol": "HTTP",
            "HealthCheckPort": "8080",
            "HealthCheckEnabled": true,
            "HealthCheckIntervalSeconds": 10,
            "HealthCheckTimeoutSeconds": 5,
            "HealthyThresholdCount": 2,
            "UnhealthyThresholdCount": 2,
            "HealthCheckPath": "/",
            "Matcher": {
                "HttpCode": "200"
            },
            "TargetType": "instance",
            "ProtocolVersion": "HTTP1",
            "IpAddressType": "ipv4"
        }
    ]
}
```


## Task 02 Deploy the Load Balancer

[Source](https://aws.amazon.com/elasticloadbalancing/)

* Create the Load Balancer

|Key|Value|
|:--|:--|
|Type|Application Load Balancer|
|Name|ELB-DEVOPSTEAM13|
|Scheme|Internal|
|Ip Address type|IPv4|
|VPC|Refer to the infra schema|
|Security group|Refer to the infra schema|
|Listeners Protocol and port|Refer to the infra schema|
|Target group|Your own target group created in task 01|

Provide the following answers (leave any
field not mentioned at its default value):

```bash
[INPUT]
aws elbv2 create-load-balancer \
    --name ELB-DEVOPSTEAM13 \
    --subnets subnet-0d8d40fde23f8b6cc subnet-00805c7306d20a784 \
    --security-groups sg-0a35e6d8e232f46eb \
    --scheme internal \
    --type application \
    --ip-address-type ipv4 \
    --tags 'Key=Name,Value=ELB-DEVOPSTEAM13'
[OUTPUT]
{
    "LoadBalancers": [
        {
            "LoadBalancerArn": "arn:aws:elasticloadbalancing:eu-west-3:709024702237:loadbalancer/app/ELB-DEVOPSTEAM13/2fe4aa1be2fdd431",
            "DNSName": "internal-ELB-DEVOPSTEAM13-10252911.eu-west-3.elb.amazonaws.com",
            "CanonicalHostedZoneId": "Z3Q77PNBQS71R4",
            "CreatedTime": "2024-03-28T17:03:20.320000+00:00",
            "LoadBalancerName": "ELB-DEVOPSTEAM13",
            "Scheme": "internal",
            "VpcId": "vpc-03d46c285a2af77ba",
            "State": {
                "Code": "provisioning"
            },
            "Type": "application",
            "AvailabilityZones": [
                {
                    "ZoneName": "eu-west-3b",
                    "SubnetId": "subnet-00805c7306d20a784",
                    "LoadBalancerAddresses": []
                },
                {
                    "ZoneName": "eu-west-3a",
                    "SubnetId": "subnet-0d8d40fde23f8b6cc",
                    "LoadBalancerAddresses": []
                }
            ],
            "SecurityGroups": [
                "sg-0a35e6d8e232f46eb"
            ],
            "IpAddressType": "ipv4"
        }
    ]
}
```

* Get the ELB FQDN (DNS NAME - A Record)

```bash
[INPUT]
aws elbv2 describe-load-balancers \
    --names ELB-DEVOPSTEAM13 \
    --query 'LoadBalancers[0].DNSName' \
    --output text

[OUTPUT]
internal-ELB-DEVOPSTEAM13-10252911.eu-west-3.elb.amazonaws.com
```
* Add target group in our 2 instances
![target_groups](image.png)

* add listener
![listener](image-1.png)
* Get the ELB deployment status

Note : In the EC2 console select the Target Group. In the
       lower half of the panel, click on the **Targets** tab. Watch the
       status of the instance go from **unused** to **initial**.

* Ask the DMZ administrator to register your ELB with the reverse proxy via the private teams channel

* Update your string connection to test your ELB and test it

```bash
ssh devopsteam13@15.188.43.46 -i CLD_KEY_DMZ_DEVOPSTEAM13.pem -L 8888:internal-ELB-DEVOPSTEAM13-10252911.eu-west-3.elb.amazonaws.com:8080
```

* Test your application through your ssh tunneling

```bash
[INPUT]
curl localhost:8888

[OUTPUT]
<!DOCTYPE html>
<html lang="en" dir="ltr" style="--color--primary-hue:202;--color--primary-saturation:79%;--color--primary-lightness:50">
  <head>
    <meta charset="utf-8" />
<meta name="Generator" content="Drupal 10 (https://www.drupal.org)" />
<meta name="MobileOptimized" content="width" />
<meta name="HandheldFriendly" content="true" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="icon" href="/core/themes/olivero/favicon.ico" type="image/vnd.microsoft.icon" />
<link rel="alternate" type="application/rss+xml" title="" href="http://localhost:8080/rss.xml" />
<link rel="alternate" type="application/rss+xml" title="" href="http://localhost/rss.xml" />

    <title>Welcome! | My blog</title>
    <link rel="stylesheet" media="all" href="/sites/default/files/css/css_rxWlMU0vkf9T-B5GQ1r_lLr9DgtmktnxGWDBbmRtE2g.css?delta=0&amp;language=en&amp;theme=olivero&amp;include=eJxdjMEKAyEMBX9ord8U9dUNzZqSuIp_X-jBQi9zmIHx5R1XTOQ4VHjANFbRRBK8L-FWt37rhKGEtEISza8dnkA5BmN6_PJxabnl92s0uFJnbcGRtRWytaODLJ9hcsG_a2Sm8wMVPz8c" />
<link rel="stylesheet" media="all" href="/sites/default/files/css/css_DFSXYGOd21oCEC9iHSn_liySC5Mxbn7crMZ42XEyHao.css?delta=1&amp;language=en&amp;theme=olivero&amp;include=eJxdjMEKAyEMBX9ord8U9dUNzZqSuIp_X-jBQi9zmIHx5R1XTOQ4VHjANFbRRBK8L-FWt37rhKGEtEISza8dnkA5BmN6_PJxabnl92s0uFJnbcGRtRWytaODLJ9hcsG_a2Sm8wMVPz8c" />



<link rel="preload" href="/core/themes/olivero/fonts/metropolis/Metropolis-Regular.woff2" as="font" type="font/woff2" crossorigin>
<link rel="preload" href="/core/themes/olivero/fonts/metropolis/Metropolis-SemiBold.woff2" as="font" type="font/woff2" crossorigin>
<link rel="preload" href="/core/themes/olivero/fonts/metropolis/Metropolis-Bold.woff2" as="font" type="font/woff2" crossorigin>
<link rel="preload" href="/core/themes/olivero/fonts/lora/lora-v14-latin-regular.woff2" as="font" type="font/woff2" crossorigin>
    <noscript><link rel="stylesheet" href="/core/themes/olivero/css/components/navigation/nav-primary-no-js.css?s9xvcb" />
</noscript>
  </head>
  <body class="path-frontpage">
        <a href="#main-content" class="visually-hidden focusable skip-link">
      Skip to main content
    </a>

      <div class="dialog-off-canvas-main-canvas" data-off-canvas-main-canvas>

<div id="page-wrapper" class="page-wrapper">
  <div id="page">

          <header id="header" class="site-header" data-drupal-selector="site-header" role="banner">

                <div class="site-header__fixable" data-drupal-selector="site-header-fixable">
          <div class="site-header__initial">
            <button class="sticky-header-toggle" data-drupal-selector="sticky-header-toggle" role="switch" aria-controls="site-header__inner" aria-label="Sticky header" aria-checked="false">
              <span class="sticky-header-toggle__icon">
                <span></span>
                <span></span>
                <span></span>
              </span>
            </button>
          </div>

                    <div id="site-header__inner" class="site-header__inner" data-drupal-selector="site-header-inner">
            <div class="container site-header__inner__container">




<div id="block-olivero-site-branding" class="site-branding block block-system block-system-branding-block">


    <div class="site-branding__inner">
              <div class="site-branding__text">
        <div class="site-branding__name">
          <a href="/" title="Home" rel="home">My blog</a>
        </div>
      </div>
      </div>
</div>

<div class="header-nav-overlay" data-drupal-selector="header-nav-overlay"></div>


                              <div class="mobile-buttons" data-drupal-selector="mobile-buttons">
                  <button class="mobile-nav-button" data-drupal-selector="mobile-nav-button" aria-label="Main Menu" aria-controls="header-nav" aria-expanded="false">
                    <span class="mobile-nav-button__label">Menu</span>
                    <span class="mobile-nav-button__icon"></span>
                  </button>
                </div>

                <div id="header-nav" class="header-nav" data-drupal-selector="header-nav">

<div class="search-block-form block block-search-narrow" data-drupal-selector="search-block-form" id="block-olivero-search-form-narrow" role="search">


      <div class="content">
      <form action="/search/node" method="get" id="search-block-form" accept-charset="UTF-8" class="search-form search-block-form">
  <div class="js-form-item form-item js-form-type-search form-item-keys js-form-item-keys form-no-label">
      <label for="edit-keys" class="form-item__label visually-hidden">Search</label>
        <input title="Enter the terms you wish to search for." placeholder="Search by keyword or phrase." data-drupal-selector="edit-keys" type="search" id="edit-keys" name="keys" value="" size="15" maxlength="128" class="form-search form-element form-element--type-search form-element--api-search" />

        </div>
<div data-drupal-selector="edit-actions" class="form-actions js-form-wrapper form-wrapper" id="edit-actions"><button class="button--primary search-form__submit button js-form-submit form-submit" data-drupal-selector="edit-submit" type="submit" id="edit-submit" value="Search">
    <span class="icon--search"></span>
    <span class="visually-hidden">Search</span>
</button>

</div>

</form>

    </div>
  </div>
<nav  id="block-olivero-main-menu" class="primary-nav block block-menu navigation menu--main" aria-labelledby="block-olivero-main-menu-menu" role="navigation">

  <h2 class="visually-hidden block__title" id="block-olivero-main-menu-menu">Main navigation</h2>






    <ul  class="menu primary-nav__menu primary-nav__menu--level-1" data-drupal-selector="primary-nav-menu--level-1">




        <li class="primary-nav__menu-item primary-nav__menu-item--link primary-nav__menu-item--level-1">

                      <a href="/" class="primary-nav__menu-link primary-nav__menu-link--link primary-nav__menu-link--level-1 is-active" data-drupal-selector="primary-nav-menu-link-has-children" data-drupal-link-system-path="&lt;front&gt;">            <span class="primary-nav__menu-link-inner primary-nav__menu-link-inner--level-1">Home</span>
          </a>


                  </li>
          </ul>



  </nav>




  <div class="region region--secondary-menu">
    <div class="search-block-form block block-search-wide" data-drupal-selector="search-block-form-2" id="block-olivero-search-form-wide" role="search">


      <button class="block-search-wide__button" aria-label="Search Form" data-drupal-selector="block-search-wide-button">
      <svg xmlns="http://www.w3.org/2000/svg" width="22" height="23" viewBox="0 0 22 23">
  <path fill="currentColor" d="M21.7,21.3l-4.4-4.4C19,15.1,20,12.7,20,10c0-5.5-4.5-10-10-10S0,4.5,0,10s4.5,10,10,10c2.1,0,4.1-0.7,5.8-1.8l4.5,4.5c0.4,0.4,1,0.4,1.4,0S22.1,21.7,21.7,21.3z M10,18c-4.4,0-8-3.6-8-8s3.6-8,8-8s8,3.6,8,8S14.4,18,10,18z"/>
</svg>
      <span class="block-search-wide__button-close"></span>
    </button>

        <div class="block-search-wide__wrapper" data-drupal-selector="block-search-wide-wrapper" tabindex="-1">
      <div class="block-search-wide__container">
        <div class="block-search-wide__grid">
          <form action="/search/node" method="get" id="search-block-form--2" accept-charset="UTF-8" class="search-form search-block-form">
  <div class="js-form-item form-item js-form-type-search form-item-keys js-form-item-keys form-no-label">
      <label for="edit-keys--2" class="form-item__label visually-hidden">Search</label>
        <input title="Enter the terms you wish to search for." placeholder="Search by keyword or phrase." data-drupal-selector="edit-keys" type="search" id="edit-keys--2" name="keys" value="" size="15" maxlength="128" class="form-search form-element form-element--type-search form-element--api-search" />

        </div>
<div data-drupal-selector="edit-actions" class="form-actions js-form-wrapper form-wrapper" id="edit-actions--2"><button class="button--primary search-form__submit button js-form-submit form-submit" data-drupal-selector="edit-submit" type="submit" id="edit-submit--2" value="Search">
    <span class="icon--search"></span>
    <span class="visually-hidden">Search</span>
</button>

</div>

</form>

        </div>
      </div>
    </div>
  </div>
<nav  id="block-olivero-account-menu" class="block block-menu navigation menu--account secondary-nav" aria-labelledby="block-olivero-account-menu-menu" role="navigation">

  <span class="visually-hidden" id="block-olivero-account-menu-menu">User account menu</span>




          <ul class="menu secondary-nav__menu secondary-nav__menu--level-1">




        <li class="secondary-nav__menu-item secondary-nav__menu-item--link secondary-nav__menu-item--level-1">
          <a href="/user/login" class="secondary-nav__menu-link secondary-nav__menu-link--link secondary-nav__menu-link--level-1" data-drupal-link-system-path="user/login">Log in</a>

                  </li>
          </ul>



  </nav>

  </div>

                </div>
                          </div>
          </div>
        </div>
      </header>

    <div id="main-wrapper" class="layout-main-wrapper layout-container">
      <div id="main" class="layout-main">
        <div class="main-content">
          <a id="main-content" tabindex="-1"></a>

          <div class="main-content__container container">


  <div class="region region--highlighted grid-full layout--pass--content-medium">
    <div data-drupal-messages-fallback class="hidden messages-list"></div>

  </div>





                          <main role="main">


  <div class="region region--content-above grid-full layout--pass--content-medium">


<div id="block-olivero-page-title" class="block block-core block-page-title-block">



  <h1 class="title page-title">Welcome!</h1>



</div>

  </div>



  <div class="region region--content grid-full layout--pass--content-medium" id="content">


<div id="block-olivero-content" class="block block-system block-system-main-block">


      <div class="block__content">
      <div class="views-element-container">
<div class="view view-frontpage view-id-frontpage view-display-id-page_1 grid-full layout--pass--content-narrow js-view-dom-id-2e5c599a259d79b19834b81b6772da11c5102244fa6daa89c7fd4bd14c890bbe">





<div class="text-content">
  <p><em>You haven’t created any frontpage content yet.</em></p>
  <h2>Congratulations and welcome to the Drupal community.</h2>
  <p>Drupal is an open source platform for building amazing digital experiences. It’s made, used, taught, documented, and marketed by the <a href="https://www.drupal.org/community">Drupal community</a>. Our community is made up of people from around the world with a shared set of <a href="https://www.drupal.org/about/values-and-principles">values</a>, collaborating together in a respectful manner. As we like to say:</p>
  <blockquote>Come for the code, stay for the community.</blockquote>
  <h2>Get Started</h2>
  <p>There are a few ways to get started with Drupal:</p>
  <ol>
    <li><a href="https://www.drupal.org/docs/user_guide/en/index.html">User Guide:</a> Includes installing, administering, site building, and maintaining the content of a Drupal website.</li>
    <li><a href="/node/add">Create Content:</a> Want to get right to work? Start adding content. <strong>Note:</strong> the information on this page will go away once you add content to your site. Read on and bookmark resources of interest.</li>
    <li><a href="https://www.drupal.org/docs/extending-drupal">Extend Drupal:</a> Drupal’s core software can be extended and customized in remarkable ways. Install additional functionality and change the look of your site using addons contributed by our community.</li>
  </ol>
  <h2>Next Steps</h2>
  <p>Bookmark these links to our active Drupal community groups and support resources.</p>
  <ul>
    <li><a href="https://groups.drupal.org/global-training-days">Global Training Days:</a> Helpful information for evaluating Drupal as a framework and as a career path. Taught in your local language.</li>
    <li><a href="https://www.drupal.org/community/events">Upcoming Events:</a> Learn and connect with others at conferences and events held around the world.</li>
    <li><a href="https://www.drupal.org/community">Community Page:</a> List of key Drupal community groups with their own content.</li>
    <li>Get support and chat with the Drupal community on <a href="https://www.drupal.org/slack">Slack</a> or <a href="https://www.drupal.org/drupalchat">DrupalChat</a>. When you’re looking for a solution to a problem, go to <a href="https://drupal.stackexchange.com/">Drupal Answers on Stack Exchange</a>.</li>
  </ul>
</div>



</div>
</div>

    </div>
  </div>

  </div>

              </main>

          </div>
        </div>
        <div class="social-bar">

<div class="social-bar__inner fixable">
  <div class="rotate">


<div id="block-olivero-syndicate" role="complementary" class="block block-node block-node-syndicate-block">


      <div class="block__content">



<a href="/rss.xml" class="feed-icon">
  <span class="feed-icon__label">
    RSS feed
  </span>
  <span class="feed-icon__icon" aria-hidden="true">
    <svg xmlns="http://www.w3.org/2000/svg" width="14.2" height="14.2" viewBox="0 0 14.2 14.2">
  <path d="M4,12.2c0-2.5-3.9-2.4-3.9,0C0.1,14.7,4,14.6,4,12.2z M9.1,13.4C8.7,9,5.2,5.5,0.8,5.1c-1,0-1,2.7-0.1,2.7c3.1,0.3,5.5,2.7,5.8,5.8c0,0.7,2.1,0.7,2.5,0.3C9.1,13.7,9.1,13.6,9.1,13.4z M14.2,13.5c-0.1-3.5-1.6-6.9-4.1-9.3C7.6,1.7,4.3,0.2,0.8,0c-1,0-1,2.6-0.1,2.6c5.8,0.3,10.5,5,10.8,10.8C11.5,14.5,14.3,14.4,14.2,13.5z"/>
</svg>
  </span>
</a>

    </div>
  </div>

  </div>
</div>

        </div>
      </div>
    </div>

    <footer class="site-footer">
      <div class="site-footer__inner container">



  <div class="region region--footer-bottom grid-full layout--pass--content-medium">


<div id="block-olivero-powered" class="block block-system block-system-powered-by-block">



  <span>
    Powered by    <a href="https://www.drupal.org">Drupal</a>
    <span class="drupal-logo" role="img" aria-label="Drupal Logo">
      <svg width="14" height="19" viewBox="0 0 42.15 55.08" fill="none" xmlns="http://www.w3.org/2000/svg">
<path d="M29.75 11.73C25.87 7.86 22.18 4.16 21.08 0 20 4.16 16.28 7.86 12.4 11.73 6.59 17.54 0 24.12 0 34a21.08 21.08 0 1042.15 0c0-9.88-6.59-16.46-12.4-22.27zM10.84 35.92a14.13 14.13 0 00-1.65 2.62.54.54 0 01-.36.3h-.18c-.47 0-1-.92-1-.92-.14-.22-.27-.45-.4-.69l-.09-.19C5.94 34.25 7 30.28 7 30.28a17.42 17.42 0 012.52-5.41 31.53 31.53 0 012.28-3l1 1 4.72 4.82a.54.54 0 010 .72l-4.93 5.47zm10.48 13.81a7.29 7.29 0 01-5.4-12.14c1.54-1.83 3.42-3.63 5.46-6 2.42 2.58 4 4.35 5.55 6.29a3.08 3.08 0 01.32.48 7.15 7.15 0 011.3 4.12 7.23 7.23 0 01-7.23 7.25zM35 38.14a.84.84 0 01-.67.58h-.14a1.22 1.22 0 01-.68-.55 37.77 37.77 0 00-4.28-5.31l-1.93-2-6.41-6.65a54 54 0 01-3.84-3.94 1.3 1.3 0 00-.1-.15 3.84 3.84 0 01-.51-1v-.19a3.4 3.4 0 011-3c1.24-1.24 2.49-2.49 3.67-3.79 1.3 1.44 2.69 2.82 4.06 4.19a57.6 57.6 0 017.55 8.58A16 16 0 0135.65 34a14.55 14.55 0 01-.65 4.14z"/>
</svg>
    </span>
  </span>
</div>

  </div>

      </div>
    </footer>

    <div class="overlay" data-drupal-selector="overlay"></div>

  </div>
</div>

  </div>


    <script type="application/json" data-drupal-selector="drupal-settings-json">{"path":{"baseUrl":"\/","pathPrefix":"","currentPath":"node","currentPathIsAdmin":false,"isFront":true,"currentLanguage":"en"},"pluralDelimiter":"\u0003","suppressDeprecationErrors":true,"ajaxTrustedUrl":{"\/search\/node":true},"user":{"uid":0,"permissionsHash":"78d894c370703dd7564941b21e89c3d56c454900480edee3118ffb015fa2e159"}}</script>
<script src="/sites/default/files/js/js_DXcy_sx9lRHKToNNmvmP14mnZf9yABZQhYQDEhBvenc.js?scope=footer&amp;delta=0&amp;language=en&amp;theme=olivero&amp;include=eJxdjMEKAyEMBX9ord8U9dUNzZqSuIp_X-jBQi9zmIHx5R1XTOQ4VHjANFbRRBK8L-FWt37rhKGEtEISza8dnkA5BmN6_PJxabnl92s0uFJnbcGRtRWytaODLJ9hcsG_a2Sm8wMVPz8c"></script>

  </body>
</html>
```

#### Questions - Analysis

* On your local machine resolve the DNS name of the load balancer into
  an IP address using the `nslookup` command (works on Linux, macOS and Windows). Write
  the DNS name and the resolved IP Address(es) into the report.

```
[INPUT]
 nslookup internal-ELB-DEVOPSTEAM13-10252911.eu-west-3.elb.amazonaws.com
 [OUTPUT]
Server:         172.27.160.1
Address:        172.27.160.1#53

Non-authoritative answer:
Name:   internal-ELB-DEVOPSTEAM13-10252911.eu-west-3.elb.amazonaws.com
Address: 10.0.13.132
Name:   internal-ELB-DEVOPSTEAM13-10252911.eu-west-3.elb.amazonaws.com
Address: 10.0.13.13
```

* From your Drupal instance, identify the ip from which requests are sent by the Load Balancer.

Help : execute `tcpdump port 8080`

```
ssh devopsteam13@15.188.43.46 -i ~/.ssh/CLD_KEY_DMZ_DEVOPSTEAM13.pem -Nv \
-L 1313:10.0.13.10:22 \
-L 8080:internal-ELB-DEVOPSTEAM13-10252911.eu-west-3.elb.amazonaws.com:8080

then 
 ssh bitnami@localhost -p 1313 -i CLD_KEY_DRUPAL_DEVOPSTEAM13.pem
sudo tcpdump port 8080

```

```
LOgS:
18:57:56.763959 IP 10.0.13.10.http-alt > 10.0.13.132.47810: Flags [P.], seq 1:5623, ack 131, win 489, options [nop,nop,TS val 681256550 ecr 1686117262], length 5622: HTTP: HTTP/1.1 200 OK
```
We can see that the IP is: 10.0.13.132

* In the Apache access log identify the health check accesses from the
  load balancer and copy some samples into the report.

```
[INPUT]
cat /opt/bitnami/apache/logs/access_logs
10.0.13.132 - - [28/Mar/2024:18:09:24 +0000] "GET / HTTP/1.1" 500 67
10.0.13.13 - - [28/Mar/2024:18:09:31 +0000] "GET / HTTP/1.1" 500 67
```
