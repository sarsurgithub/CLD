# C2 - Load Balancer

## Create and deploy a Web Load Balancer

****[**AWS Official doc**](https://aws.amazon.com/elasticloadbalancing/)****

### **Step 1: Create a Target Group**

| Setting          | Value                                                                                                                                                                                           |
| ---------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Basic config     | <ul><li>Target type: instances</li><li>Target group name: CLD-LB-TG-DEVOPSTEAM[XX]</li><li>Protocol : HTTP</li><li>Port : 8080</li><li>VPC : VPC-CLD</li><li>Protocol version : HTTP1</li></ul> |
| Health checks    | <ul><li>HTTP</li><li>Health check path: "/drupal" or "/" (depending on your config)</li><li>Advanced health check settings: no change required</li></ul>                                        |
| Attributes       | none                                                                                                                                                                                            |
| Tags - optional  | <ul><li>Key : Name</li><li>Value :  CLD-LB-TG-DEVOPSTEAM[XX]</li></ul>                                                                                                                          |
| \*\*\*\*         | \*\*\*                                                                                                                                                                                          |
| Register targets | <ul><li>Choose your instance</li><li>Ports : 8080</li><li>Include it as pending </li></ul>                                                                                                      |

```
[INPUT]
//TODO
[OUTPUT]
//TODO
```

### **Step 2: Create a Load balancer**

| Setting                         | Value                                                                                                                                                                                                                                            |
| ------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| Type of Loadbalancer            | Application Load Balancer                                                                                                                                                                                                                        |
| Configure Load Balancer         | <ul><li>Name: CLD-LB-DEVOPSTEAM[XX]</li><li>Scheme: Internal</li><li>IP address type: ipv4</li></ul>                                                                                                                                             |
| Network mapping                 | <ul><li>VPC: VPC-CLD</li><li><p>SUBNETS:</p><ul><li>10.0.[XX].0/28 -> eu-south-1a</li><li>10.0.[XX[.128/28 -> eu-south-1b</li></ul></li></ul>                                                                                                    |
| Configure Security Groups       | <ul><li>Name : CLD-SG-LB-DEVOPSTEAM[XX]</li><li><p>Inbound rules:</p><ul><li>Type: Custom TCP</li><li>Port range: 8080</li><li>Source : DMZ</li></ul></li></ul>                                                                                  |
| Configure Listeners and routing | <ul><li>Protocol: HTTP</li><li>Port: 8080</li><li><p>Default action (forward to):</p><ul><li>CLD-LB-TG-DEVOPSTEAM[XX]</li></ul></li><li><p>Listener tags - optional:</p><ul><li>Key: Name</li><li>Value: CLD-LT-DEVOSPTEAM99</li></ul></li></ul> |
| Tags - optional                 | <ul><li>Key: Name</li><li>Value: CLD-LB-DEVOPSTEAM[99]</li></ul>                                                                                                                                                                                 |

```
[INPUT]
//TODO
[OUTPUT]
//TODO
```

* Announce your Loadbalancer FQDN in your private Teams channel (tag Nicolas Glassey or Rémi Poulard) like this:
  * FQDN + application root url ("/" or "/drupal")
* Wait for a response from Nicolas or Rémi
* Test your infra by using devopsteam\[XX].cld.education in a browser

### **Step 3: Add an instance**

* Using the custom virtual machine image you created earlier launch a second instance (in the b sunbet with ip address 10.0.\[XX]140).
* Add this instance to the Loadbalancer target group

```
[INPUT]
//TODO
[OUTPUT]
//TODO
```

* Does your new instance access to the web ?

### Conceptual aspects

* Question 1 - Why does AWS need multiple AZ for Elasticity?
  * &#x20;[Official Doc](https://docs.aws.amazon.com/elasticloadbalancing/latest/userguide/how-elastic-load-balancing-works.html)
* Question 2 - How to prove the correct operation of the load balancer?

### Debug

* [How to debug Load Balancer?](https://docs.aws.amazon.com/elasticloadbalancing/latest/application/target-group-health-checks.html)
* [Update Time out config on Load Balancer?](https://aws.amazon.com/blogs/aws/elb-idle-timeout-control/)
* Check the security group... the Load Balancer is hosted in the same private subnets as your drupal instance...
* If your web app drupal does not run under "/drupal" but directly as the root path "/", please inform the assistant (impact on the reverse proxy setting)
