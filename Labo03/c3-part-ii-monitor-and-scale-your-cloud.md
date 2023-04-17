# L3 - Part II - Monitor and scale your cloud

### Use Case

Case 1 : As soon as the CPU load of the main Drupal instance reach X%, the auto-scaling mechanism must launch new instances (horizontal scaling) to absorb this load.

Case 2 : As soon as the load goes down, we must observe the instances reduce in number until the return to normal operation status. &#x20;

### Step 0 - Read the doc

* [AWS - What is EC2 auto-scaling ?](https://docs.aws.amazon.com/autoscaling/ec2/userguide/what-is-amazon-ec2-auto-scaling.html)

### Step 1 - Create a launch template

* [AWS - Tutorial](https://docs.aws.amazon.com/autoscaling/ec2/userguide/GettingStartedTutorial.html#gs-create-lt)
* [AWS - Launch template or configuration ?](https://docs.aws.amazon.com/autoscaling/ec2/userguide/launch-templates.html)

<figure><img src="../../../.gitbook/assets/image (2).png" alt=""><figcaption></figcaption></figure>

| Variable Name                | Variable Value                                    |
| ---------------------------- | ------------------------------------------------- |
| Name                         | CLD-LC-DEVOPSTEAM\[XX]                            |
| Template version description | As the template name                              |
| Template Tags                | <p>Key : Name<br>Value : As the template name</p> |
| AMI                          | Your own Drupal AMI                               |
| Instance type                | t3.micro                                          |
| Key Pair (login)             | As the original instance                          |
| Subnet                       | not included in template (see ASG below)          |
| Security Groups              | As the original instance                          |
| Storage                      | 1 Volume, 1 GiB, SSD (gp2)                        |

* [AWS CLI Doc - create-launch template](https://awscli.amazonaws.com/v2/documentation/api/latest/reference/ec2/create-launch-template.html)

```
//TODO CLI COMMAND
[INPUT]

[OUTPUT]
```

### Step 2 - Create Auto Scaling group

* [AWS - Tutorial](https://docs.aws.amazon.com/autoscaling/ec2/userguide/GettingStartedTutorial.html#gs-create-asg)

| Variable Name                  | Variable Value                                                                                                               |
| ------------------------------ | ---------------------------------------------------------------------------------------------------------------------------- |
| Name                           | CLD-ASG-DEVOPSTEAM\[XX]                                                                                                      |
| Launch template                | Your own launch template created previously                                                                                  |
| Version                        | Latest                                                                                                                       |
| VPC                            | VPC-CLD                                                                                                                      |
| Availability Zones and Subnets | Your own subnets A et B                                                                                                      |
| Load balancing                 | No load balancer                                                                                                             |
| Health checks                  | Grace period : 300 seconds                                                                                                   |
| Additional settings            | <p>Monitoring : disabled<br>Default instance warmup : disabled</p>                                                           |
| Groupe size                    | <p>Desired capacity : 0<br>Minimum capacity : 0<br>Maximum capacity : 4</p>                                                  |
| Scaling policies               | <p>Target tracking scaling policy<br>Metric type : Average CPU utilization<br>Target value : 30<br>Second warm up : none</p> |
| Instance scale-in protection   | Disabled                                                                                                                     |
| Notification (SNS)             | None                                                                                                                         |
| Tags                           | <p>Key : Name<br>Value : As the auto-scaling-group name</p>                                                                  |



* [AWS CLI Doc - create-auto-scaling group](https://awscli.amazonaws.com/v2/documentation/api/latest/reference/autoscaling/create-auto-scaling-group.html)

```
//TODO CLI COMMAND
[INPUT]

[OUTPUT]
```

### Step 3 - Experiment with your config

* Using Stress as in Part I, try to activate your auto-scaling group and observe the instances automatically launched.

```
//TODO VIDEO OF THE SEQUENCE
(remove the images during which you are waiting for the system to react)
```

* After Stress sequence, observe how your infra returns to "normal" operation.

```
//TODO VIDEO OF THE SEQUENCE 
(remove the images during which you are waiting for the system to react)
```

