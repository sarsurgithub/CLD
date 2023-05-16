# L5 - Part II - Deploy the application in Kubernetes Engine

## Subtask 2.1 - Create a project

```
[INPUT]
//TODO gcloud command line (project name : DEVOPSTEAMXX-KUBERNETES)

[OUTPUT]
//TODO
```

## Subtask 2.2 - Create a cluster

```
[INPUT]
//TODO

[OUTPUT]
//TODO
```

## Subtask 2.3 - Deploy the application on the cluster

* [ ] Display available context

```
[INPUT]
kubectl config get-contexts

[OUTPUT]
//TODO
```

{% hint style="info" %}
Now, we will repeat the application deployment steps (Part 1.3 or this labo). The steps have been copied below.
{% endhint %}

* [ ] Deploy the redis service and pod

```
[INPUT]
///

[OUTPUT]
///
```

* [ ] Zoom in on a Kubernetes object and see much more detail

```
[INPUT]
//TODO

[OUTPUT]
//TODO
```

```
[INPUT]
//TODO

[OUTPUT]
//TODO
```

* [ ] Deploy the TODO-API Service and Pod

```
[INPUT]
///

[OUTPUT]
///
```

* [ ] Verify that they are up and running on the correct ports

```
[INPUT]
///

[OUTPUT]
///
```

## Subtask 2.4 - Deploy the frontend Service

```
[INPUT]
///

[OUTPUT]
///
```

* [ ] Monitor the creation of the load balancer using :

```
[INPUT]
kubectl describe

[OUTPUT]
//TODO
```

Verify the TODO Application

* [ ] Find out the public URL of the frontend load balancer

```
INPUT]
///

[OUTPUT]
///
```

* [ ] Access the public URL of the Service with a browser. You should be able to access the complete application and create a new Todo

```
//TODO GUI Screen shot
```

