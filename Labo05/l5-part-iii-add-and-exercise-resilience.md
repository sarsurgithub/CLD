# L5 - Part III - Add and Exercise Resilience

## Subtask 3.1 - Add Deployments

* [ ] Create a deployment version of your application (redis-deploy.yaml instead of redis-pod.yaml) and modify/extend them to contain the required deployment parameters

```
//TODO Add both .yaml files in your repo (do not copy them here)
```

* [ ] Make sure to have always 2 instances of the API and Frontend running.

```
[INPUT]
//TODO

[OUTPUT]
//TODO
```

* [ ] Use only 1 instance for the Redis-Server. Why ?

```
//TODO
```

* [ ] Delete all application Pods and replace them with deployment versions

```
[INPUT]
///
```

* [ ] Verify that the application is still working and the Replica Sets are in place

```
[INPUT]
//TODO

[OUTPUT]
///
```

## Subtask 3.2 - Verify the functionality of the replica sets

* [ ] What happens if you delete a Frontend or API Pod?

```
[INPUT]
//TODO

[OUTPUT]
//TODO
```

* [ ] How long does it take for the system to react?

```
[INPUT]
//TODO

[OUTPUT]
//TODO
```

* [ ] What happens when you delete the Redis Pod?

```
[INPUT]
//TODO

[OUTPUT]
//TODO
```

* [ ] How can you change the number of instances temporarily to 3?

{% hint style="info" %}
Look for scaling in the deployment documentation
{% endhint %}

```
[INPUT]
//TODO

[OUTPUT]
//TODO
```

* [ ] What autoscaling features are available?

```
[INPUT]
//TODO

[OUTPUT]
//TODO
```

* [ ] Which metrics are used?

```
[INPUT]
//TODO

[OUTPUT]
//TODO
```

* [ ] How can you update a component?

{% hint style="info" %}
see "Updating a Deployment" in the deployment documentation
{% endhint %}

```
[INPUT]
//TODO

[OUTPUT]
//TODO
```

## Subtask 3.3 (optional) - Put autoscaling in place and load-test it

* [ ] On the GKE cluster deploy autoscaling on the Frontend with a target CPU utilization of 30% and number of replicas between 1 and 4. Load-test using JMeter.

```
[INPUT]
//TODO

[OUTPUT]
//TODO
```

## Clean up

At the end of the lab session:

* Delete the GKE cluster
* Delete the project
*   If you want to remove the Minikube Virtual Machine from your local machine, run

    ```
    $ minikube stop
    $ minikube delete
    ```





