# L3 - Part I - Loading tools

### Step 0 - Testing policy

* [AWS Penetration Testing](https://aws.amazon.com/security/penetration-testing/)
* [AWS DDoS Testing Policy ](https://aws.amazon.com/security/ddos-simulation-testing/)

### Step 1 - JMeter

* [ ] Download and install on your local machine the JMeter tool from [http://jmeter.apache.org/](http://jmeter.apache.org/).

```
//TODO JMeter download checksum
```

* [ ] Open two terminal windows side-by-side and, using SSH, log into each instance. Bring up a continuous display of the Apache access log by running the command **sudo tail -f /var/log/apache2/access.log**. (path's log may differ depending on your apache config, e.g if you are using virtual host for you web app)

```
//TODO 
```

* [ ] Through the AWS console, you can enable detailed (1-minute interval) monitoring of the two instances: Select an instance and click on the **Monitoring** tab. Click on the button **Manage detailed monitoring**.

```
//TODO
https://awscli.amazonaws.com/v2/documentation/api/latest/reference/ec2/monitor-instances.html

[INPUT]

[OUTPUT
```

* [ ] Consult the JMeter documentation [https://jmeter.apache.org/usermanual/build-web-test-plan.html](https://jmeter.apache.org/usermanual/build-web-test-plan.html) and create a simple test plan. Specify the load balancer as the target for the HTTP requests. Run a test load test based on http request listener.

```
//TODO JMeter config file
```

* [ ] Observe which of the instances gets the load. Increase the load and re-run the test. Observe response times and time-outs. Repeat until you see unacceptable response times and/or time-outs.

//TODO Screen shot on tail /f on both instances

* [ ] Immediately after having created a high load for the site, re-run the nslookup command to resolve the DNS name of the load balancer into IP addresses to see if there are any changes.

```
[INPUT]

[OUTPUT]

//Observations ?
```

### Step 2 - Stress

* [ ] Setup [htop](https://htop.dev/) and [stress](http://manpages.ubuntu.com/manpages/focal/man1/stress.1.html) on your drupal instance.
* [ ] Stress your instance and observe it with htop.
* [ ] Observe the monitoring view on the AWS Console.

```
//TODO
Screen shot of htop and aws monitoring graphics... we should observe the same stress "spikes".
```

### Step 3 - Analysis

* When you resolve the DNS name of the load balancer into IP addresses while the load balancer is under high load what do you see? Explain.

```
[INPUT]

[OUTPUT]

[Explanation - about ]
```

* Did this test really test the load-balancing mechanism?

```
[Explanation]
```

&#x20; What are the limitations of this simple test?&#x20;

```
[Explanation]
```

What would be necessary to do realistic testing?

```
[Explanation]
```

