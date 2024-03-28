# Custom AMI and Deploy the second Drupal instance

In this task you will update your AMI with the Drupal settings and deploy it in the second availability zone.

## Task 01 - Create AMI

### [Create AMI](https://awscli.amazonaws.com/v2/documentation/api/latest/reference/ec2/create-image.html)

Note : stop the instance before

|Key|Value for GUI Only|
|:--|:--|
|Name|AMI_DRUPAL_DEVOPSTEAM[XX]_LABO02_RDS|
|Description|Same as name value|

```bash
[INPUT]
aws ec2 stop-instances --instance-id i-0e4b9e05b37bc28d7

aws ec2 create-image \
    --instance-id i-0e4b9e05b37bc28d7 \
    --name "AMI_PRIVATE_DRUPAL_DEVOPSTEAM13_LABO02_RDS" \
    --description "AMI_PRIVATE_DRUPAL_DEVOPSTEAM13_LABO02_RDS" \
    --tag-specifications 'ResourceType=image,Tags=[{Key=Name,Value=AMI_PRIVATE_DRUPAL_DEVOPSTEAM13_LABO02_RDS}]'
```
```bash
[OUTPUT]
{
    "ImageId": "ami-018695642be82c95c"
}
```

## Task 02 - Deploy Instances

* Restart Drupal Instance in Az1

* Deploy Drupal Instance based on AMI in Az2

|Key|Value for GUI Only|
|:--|:--|
|Name|EC2_PRIVATE_DRUPAL_DEVOPSTEAM[XX]_B|
|Description|Same as name value|

```bash
[INPUT]
aws ec2 start-instances --instance-ids i-0e4b9e05b37bc28d7

aws ec2 run-instances \
   --image-id ami-018695642be82c95c \
   --count 1 \
   --instance-type t3.micro \
   --key-name CLD_KEY_DRUPAL_DEVOPSTEAM13 \
   --private-ip-address 10.0.13.140 \
   --security-group-ids sg-0622b478637553a6e sg-09117159e2be96a6e \
   --subnet-id subnet-00805c7306d20a784 \
   --tag-specifications 'ResourceType=instance,Tags=[{Key=Name,Value=EC2_PRIVATE_DRUPAL_DEVOPSTEAM13_B}]' \
   --placement AvailabilityZone=eu-west-3b
```

```bash
[OUTPUT]
{
    "Groups": [],
    "Instances": [
        {
            "AmiLaunchIndex": 0,
            "ImageId": "ami-018695642be82c95c",
            "InstanceId": "i-005a4e37a7176e780",
            "InstanceType": "t3.micro",
            "KeyName": "CLD_KEY_DRUPAL_DEVOPSTEAM13",
            "LaunchTime": "2024-03-28T17:15:26+00:00",
            "Monitoring": {
                "State": "disabled"
            },
            "Placement": {
                "AvailabilityZone": "eu-west-3b",
                "GroupName": "",
                "Tenancy": "default"
            },
            "PrivateDnsName": "ip-10-0-13-140.eu-west-3.compute.internal",
            "PrivateIpAddress": "10.0.13.140",
            "ProductCodes": [],
            "PublicDnsName": "",
            "State": {
                "Code": 0,
                "Name": "pending"
            },
            "StateTransitionReason": "",
            "SubnetId": "subnet-00805c7306d20a784",
            "VpcId": "vpc-03d46c285a2af77ba",
            "Architecture": "x86_64",
            "BlockDeviceMappings": [],
            "ClientToken": "3a33da63-f437-478b-bccc-1181b5f8a9f0",
            "EbsOptimized": false,
            "EnaSupport": true,
            "Hypervisor": "xen",
            "NetworkInterfaces": [
                {
                    "Attachment": {
                        "AttachTime": "2024-03-28T17:15:26+00:00",
                        "AttachmentId": "eni-attach-0168eb78dd950c277",
                        "DeleteOnTermination": true,
                        "DeviceIndex": 0,
                        "Status": "attaching",
                        "NetworkCardIndex": 0
                    },
                    "Description": "",
                    "Groups": [
                        {
                            "GroupName": "SG-PRIVATE-DRUPAL-DEVOPSTEAM13-RDS",
                            "GroupId": "sg-0622b478637553a6e"
                        },
                        {
                            "GroupName": "SG-PRIVATE-DRUPAL-DEVOPSTEAM13",
                            "GroupId": "sg-09117159e2be96a6e"
                        }
                    ],
                    "Ipv6Addresses": [],
                    "MacAddress": "0a:73:ac:92:29:d5",
                    "NetworkInterfaceId": "eni-0abe6f40503af72f8",
                    "OwnerId": "709024702237",
                    "PrivateIpAddress": "10.0.13.140",
                    "PrivateIpAddresses": [
                        {
                            "Primary": true,
                            "PrivateIpAddress": "10.0.13.140"
                        }
                    ],
                    "SourceDestCheck": true,
                    "Status": "in-use",
                    "SubnetId": "subnet-00805c7306d20a784",
                    "VpcId": "vpc-03d46c285a2af77ba",
                    "InterfaceType": "interface"
                }
            ],
            "RootDeviceName": "/dev/xvda",
            "RootDeviceType": "ebs",
            "SecurityGroups": [
                {
                    "GroupName": "SG-PRIVATE-DRUPAL-DEVOPSTEAM13-RDS",
                    "GroupId": "sg-0622b478637553a6e"
                },
                {
                    "GroupName": "SG-PRIVATE-DRUPAL-DEVOPSTEAM13",
                    "GroupId": "sg-09117159e2be96a6e"
                }
            ],
            "SourceDestCheck": true,
            "StateReason": {
                "Code": "pending",
                "Message": "pending"
            },
            "Tags": [
                {
                    "Key": "Name",
                    "Value": "EC2_PRIVATE_DRUPAL_DEVOPSTEAM13_B"
                }
            ],
            "VirtualizationType": "hvm",
            "CpuOptions": {
                "CoreCount": 1,
                "ThreadsPerCore": 2
            },
            "CapacityReservationSpecification": {
                "CapacityReservationPreference": "open"
            },
            "MetadataOptions": {
                "State": "pending",
                "HttpTokens": "optional",
                "HttpPutResponseHopLimit": 1,
                "HttpEndpoint": "enabled",
                "HttpProtocolIpv6": "disabled",
                "InstanceMetadataTags": "disabled"
            },
            "EnclaveOptions": {
                "Enabled": false
            },
            "PrivateDnsNameOptions": {
                "HostnameType": "ip-name",
                "EnableResourceNameDnsARecord": false,
                "EnableResourceNameDnsAAAARecord": false
            },
            "MaintenanceOptions": {
                "AutoRecovery": "default"
            },
            "CurrentInstanceBootMode": "legacy-bios"
        }
    ],
    "OwnerId": "709024702237",
    "ReservationId": "r-05bd684143459fb2c"
}
```

## Task 03 - Test the connectivity

### Update your ssh connection string to test

* add tunnels for ssh and http pointing on the B Instance

```bash
//updated string connection
ssh devopsteam13@15.188.43.46 -i ~/.ssh/CLD_KEY_DMZ_DEVOPSTEAM13.pem -Nv \
    -L 2227:10.0.13.10:22 \
    -L 8887:10.0.13.10:8080 \
    -L 2228:10.0.13.140:22 \
    -L 8888:10.0.13.140:8080 
    
// dans un autre shell
ssh bitnami@localhost -p 2227 -i ~/.ssh/CLD_KEY_DRUPAL_DEVOPSTEAM13.pem
ssh bitnami@localhost -p 2228 -i ~/.ssh/CLD_KEY_DRUPAL_DEVOPSTEAM13.pem
```

## Check SQL Accesses

```sql
[INPUT]
//sql string connection from A
mariadb -h dbi-devopsteam13.cshki92s4w5p.eu-west-3.rds.amazonaws.com \
-u bn_drupal -p2b9defd18a354804a1d4c4742c252fb39d808c12cfc2046ffc8f31432ae8a060 \
bitnami_drupal -e "SHOW GRANTS for bn_drupal@'10.0.13.%';"
[OUTPUT]

+--------------------+
| Database           |
+--------------------+
| bitnami_drupal     |
| information_schema |
+--------------------+
```

```sql
[INPUT]
//sql string connection from B
mariadb -h dbi-devopsteam13.cshki92s4w5p.eu-west-3.rds.amazonaws.com \
-u bn_drupal -p2b9defd18a354804a1d4c4742c252fb39d808c12cfc2046ffc8f31432ae8a060 \
bitnami_drupal -e "SHOW GRANTS for bn_drupal@'10.0.13.%';"
[OUTPUT]

+--------------------+
| Database           |
+--------------------+
| bitnami_drupal     |
| information_schema |
+--------------------+
```

### Check HTTP Accesses

```bash
//connection string updated
curl -I http://localhost:8887
curl -I http://localhost:8888
```

### Read and write test through the web app

* Login in both webapps (same login)

* Change the users' email address on a webapp... refresh the user's profile page on the second and validated that they are communicating with the same db (rds).

* Observations ?

```
We can see that the address has also changed in the other instance. In deed, they both share the same database. One change in it will affect every instances connected to it.
```

### Change the profil picture

* Observations ?

```
The profile picture is changed. 

However, it is not changed on every instances. The profile picture is not stored in the database but in the file system. 
```