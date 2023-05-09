# L5 - Part 1 - Deploy the application on a local test cluster

* [ ] Subtask 1.1 - Installation of Minikube

<!---->

* Setup and update Minikube

```
[INPUT]
//TODO

[OUTPUT]
//TODO
```

* Version of Minikube

```
[INPUT]
//TODO

[OUTPUT]
//TODO
```

* [ ] Subtask 1.2 - Installation of Kubectl

<!---->

* Setup and update Kubectl

```
[INPUT]
//TODO

[OUTPUT]
//TODO
```

* Version of Kubectl

```
[INPUT]
//TODO

[OUTPUT]
//TODO
```

* [ ] Subtask 1.3 Create a one-node cluster on your local machine

<!---->

* Create and start a one-node-cluster

```
[INPUT]
./minikube start

[OUTPUT]
//TODO
```

* Examine the cluster

```
[INPUT]
kubectcl cluster-info

[OUTPUT]
//TODO
```

* View the nodes in the cluster

```
[INPUT]
kubectl get nodes

[OUTPUT]
//TODO
```

* [ ] Subtask - OPTIONAL - Access the kubernetes dashboard

```
[INPUT]
minikube dashboard

[OUTPUT]
//TODO
```

* [ ] Substask 1.4 - Deploy the application

<!---->

* Deploy the redis service and pod

```
[INPUT]
kubectl create -f redis-svc.yaml
kubectl create -f redis-pod.yaml
kubectl get all

[OUTPUT]
//TODO
```

* Zoom in on a Kubernetes object and see much more detail

```
[INPUT]
kubectl describe po/redis

[OUTPUT]
//TODO
```

```
[INPUT]
kubectl desribe svc/redis-svc

[OUTPUT]
//TODO
```

* Deploy the TODO-API Service and Pod

```
[INPUT]
///

[OUTPUT]
///
```

* Verify that they are up and running on the correct ports

```
[INPUT]
///

[OUTPUT]
///
```

* Deploy the frontend Pod

```
[INPUT]
///

[OUTPUT]
///
```

* What value must be set for the API-endpoint-URL?

```
///TODO
```

* Verify the TODO Application

{% hint style="info" %}
To start port forwarding

```
kubectl port-forward pod_name local_port:pod_port
```
{% endhint %}

```
[INPUT]
///

[OUTPUT]
///
```
