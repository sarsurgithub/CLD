---
description: This page describes the Part I of the Challenge 2
---

# \[In Review] C2 - Part I - Preparing Network

### Prerequisites

* Previous laboratories have to be done.
* Please refer to [Infra Labo 02](infra-labo-02.md#infra-diagram).

### Challenge

{% hint style="info" %}
Do not change anything on VPC level, as well as the DMZ!&#x20;
{% endhint %}

#### Step 1 - Create an AMI of your Drupal Instance

Constraints:&#x20;

* AMI Name - CLD-INSTANCE-DEVOPSTEAM\[XX]

#### Step 2 - Deploy a new instance, based on your AIM previously created.

Constraints:

|                  |                                                                 |
| ---------------- | --------------------------------------------------------------- |
| Instance Name    | CLD-INSTANCE-DEVOPSTEAM\[XX] _Drupal_\_\[FREE]                  |
| Instance Type    | t3.micro                                                        |
| OS Volume        | 10 Go                                                           |
| Region           | Please refer to [Infra Labo 02](infra-labo-02.md#infra-diagram) |
| VPC              | Please refer to [Infra Labo 02](infra-labo-02.md#infra-diagram) |
| Subnet           | Please refer to [Infra Labo 02](infra-labo-02.md#infra-diagram) |
| Private IP       | Please refer to [Infra Labo 02](infra-labo-02.md#infra-diagram) |
| SSH Key Pair     | Same as in VPC LAB 01                                           |
| Security (group) | CLD-SG-PRIVATE-DEVOPSTEAM\[XX]                                  |

```
[Request]
//TODO

[Response]
//TODO
```

#### Step 3 - Terminate your "old" Drupal Instance hosted in VPC-Labo01 &#x20;

```
[Request]
//TODO

[Response]
//TODO
```

#### Step 4 - Clean all Items used for experimental purpose (Key Pair, Security Group)

```
[Request]
//TODO

[Response]
//TODO
```

#### Step 6 - Finalize your LABO02 environment

* You need to "link" your two new subnet route tables (A and B) to your subnet.

![](../../../.gitbook/assets/LinkRteTableSubnet.PNG)

```
[Request]
//TODO

[Response]
//TODO
```

* Update the route table to enable outbound traffic

```
[Request]
//TODO

[Response]
//TODO
```
