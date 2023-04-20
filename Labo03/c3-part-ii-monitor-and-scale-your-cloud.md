# L3 - Part II - Monitor and scale your cloud

### <mark style="color:red;">Scenario to be realized</mark>

```
//**Initial launching**
//given
All instances "Drupal" are terminated (do not forget to create an AMI before)
The ASG and the Template are correctly set.

//when
Waiting few secondes

//then
A first Drupal instance is in "pending" status.

//**Stress phase*
//given
One instance "Drupal" is running.
Using the stress utility, load the CPU.

//then
The CPU load exceeds 30%.

//when
A second, third and fourth "Drupal" instance are launched.

//**Return to normal phase**
//given
Stop the stress command. Via Htop you can see that the stress is below 30% again.

//when
After several minutes, the AWS Monitoring (Cloud Watch) detects the drop in load.

//then
Gradually the instances are terminated, until only one remains active.
```

### Step 0 - Read the doc

* [AWS - What is EC2 auto-scaling ?](https://docs.aws.amazon.com/autoscaling/ec2/userguide/what-is-amazon-ec2-auto-scaling.html)

### Step 1 - Create a launch template

* [AWS - Tutorial](https://docs.aws.amazon.com/autoscaling/ec2/userguide/GettingStartedTutorial.html#gs-create-lt)
* [AWS - Launch template or configuration ?](https://docs.aws.amazon.com/autoscaling/ec2/userguide/launch-templates.html)

<figure><img src="../../../.gitbook/assets/image (2) (2).png" alt=""><figcaption></figcaption></figure>

| Variable Name                | Variable Value                                                                      |
| ---------------------------- | ----------------------------------------------------------------------------------- |
| Name                         | CLD-LC-DEVOPSTEAM\[XX]                                                              |
| Template version description | As the template name                                                                |
| Template Tags                | <p>Key : Name<br>Value : As the template name</p>                                   |
| AMI                          | Your own Drupal AMI                                                                 |
| Instance type                | t3.micro                                                                            |
| Key Pair (login)             | As the original instance                                                            |
| Subnet                       | not included in template (see ASG below)                                            |
| Security Groups              | As the original instance                                                            |
| Storage                      | <mark style="color:red;">1 Volume, \[As expected by your AMI] GiB, SSD (gp2)</mark> |

* [AWS CLI Doc - create-launch template](https://awscli.amazonaws.com/v2/documentation/api/latest/reference/ec2/create-launch-template.html)

```
//TODO CLI COMMAND
[INPUT]

[OUTPUT]
```

### Step 2 - Create Auto Scaling group

* [AWS - Tutorial](https://docs.aws.amazon.com/autoscaling/ec2/userguide/GettingStartedTutorial.html#gs-create-asg)

| Variable Name                  | Variable Value                                                                                                                                                              |
| ------------------------------ | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Name                           | CLD-ASG-DEVOPSTEAM\[XX]                                                                                                                                                     |
| Launch template                | Your own launch template created previously                                                                                                                                 |
| Version                        | Latest                                                                                                                                                                      |
| VPC                            | VPC-CLD                                                                                                                                                                     |
| Availability Zones and Subnets | Your own subnets A et B                                                                                                                                                     |
| Load balancing                 | No load balancer                                                                                                                                                            |
| Health checks                  | Grace period : 300 seconds                                                                                                                                                  |
| Additional settings            | <p>Monitoring : disabled<br>Default instance warmup : disabled</p>                                                                                                          |
| Groupe size                    | <p><mark style="color:red;">Desired capacity :</mark> <mark style="color:red;">1</mark><br><mark style="color:red;">Minimum capacity : 1</mark><br>Maximum capacity : 4</p> |
| Scaling policies               | <p>Target tracking scaling policy<br>Metric type : Average CPU utilization<br>Target value : 30<br>Second warm up : none</p>                                                |
| Instance scale-in protection   | Disabled                                                                                                                                                                    |
| Notification (SNS)             | None                                                                                                                                                                        |
| Tags                           | <p>Key : Name<br>Value : As the auto-scaling-group name</p>                                                                                                                 |



* [AWS CLI Doc - create-auto-scaling group](https://awscli.amazonaws.com/v2/documentation/api/latest/reference/autoscaling/create-auto-scaling-group.html)

```
//TODO CLI COMMAND
[INPUT]

[OUTPUT]
```

### Step 3 - Experiment with your config

* <mark style="color:red;">Realize the scenario describes above and make a video. Film only the interesting phases so as not to have a video exceeding 1 min).</mark>&#x20;

```
//TODO VIDEO OF THE SEQUENCE
```

### Step 4 - Reduce the cost

{% hint style="info" %}
<mark style="color:red;">To reduce the cost on AWS, please change your ASG config like this screenshot:</mark>

![](../../../.gitbook/assets/image.png)\
<mark style="color:red;">Otherwise, an instance will be maintained and we will be charged for it.</mark>
{% endhint %}

### <mark style="color:red;">Tips</mark>

{% hint style="info" %}
Externalize the JSON content of your commands in a separate file. This helps with debugging.
{% endhint %}

```
aws ec2 create-launch-template ^
    [...]
    --launch-template-data file://data.json ^
    [...]
```
