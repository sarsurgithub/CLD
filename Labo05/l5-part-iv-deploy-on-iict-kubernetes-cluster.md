# L5 - Part IV - Deploy on IICT Kubernetes Cluster

{% hint style="info" %}
**You must  activate the VPN (even from the school) to be able to access the IICT Kubernetes cluster.**
{% endhint %}

## Subtask 4.1 Setup Kubectl



1. Log yourself on [https://rancher.iict.ch](https://rancher.iict.ch/dashboard/auth/login?local) /!\ Select "Use a local user" /!\ with the credentials given in cyberlearns (csv files) on the course page.
2. Once you are logged in, click on "iict-student" cluster and then on the right corner click on the icon with the name "Download KubeConfig". Copy the content and paste it to your machine into the file `~/.kube/config`. YOu may need to merge the file with the content already present from previous step.
3. To test that you can talk to the cluster try the command `kubectl get nodes`. You should get an output similar to:

```
NAME           STATUS   ROLES                      AGE    VERSION
node1-ens   Ready    controlplane,etcd,worker   85d   v1.24.9
node2-ens   Ready    controlplane,etcd,worker   85d   v1.24.9
node3-ens   Ready    controlplane,etcd,worker   85d   v1.24.9
node4-ens   Ready    controlplane,etcd,worker   85d   v1.24.9
```

```
[INPUT]
//TODO

[OUTPUT]
//TODO
```

## Subtask 4.2 - Deploy the application

As you are all on the same cluster, to avoid one group to interact with resources of an other group we use the notion of **namespace** [namespace's documentation](https://kubernetes.io/docs/concepts/overview/working-with-objects/namespaces/). So we created one namespace for each group. In all next commands you'll need to give on which namespace the command belongs to and you do that with the parameter `-n <namespace>`. You can see [this link](https://kubernetes.io/docs/tasks/access-application-cluster/configure-access-multiple-clusters/) to add the config of the namespace directly on the config file to avoid the usage of -n. You will need to modify your deployment files. Indeed, we have a private registry at IICT to store docker images.

That means you have to change the parameter `image` in deployment files to these values:

* registry.iict.ch/cld/cld-docker-images/icclabcna/ccp2-k8s-todo-frontend
* registry.iict.ch/cld/cld-docker-images/icclabcna/ccp2-k8s-todo-api
* registry.iict.ch/cld/cld-docker-images/redis

<!---->

* [ ] Deploy all components needed with the command `kubectl apply -f <file> -n l6grx` where x is the letter of your group.&#x20;

```
[INPUT]
//TODO

[OUTPUT]
//TODO
```

* [ ] Then with the command `kubectl get all -n l6grx` you should have all information needed to connect to the frontend.

```
[INPUT]
//TODO

[OUTPUT]
//TODO
```

