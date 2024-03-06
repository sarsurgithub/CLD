# CLD-LABO"

#### Tasks

In this lab you will perform a number of tasks and document your
progress in a lab report (PDF file). Each task specifies one or more
deliverables to be produced. Collect all the deliverables in your lab
report. Give the lab report a structure that mimics the structure of
this document.

#### Allocating and freeing cloud resources

HEIG-VD is paying for your use of Amazon cloud. Please use the service
responsibly: 

* Launch virtual machines and other resources only when you are ready
  to work with them.
* Shut down virtual machines when not in use, for example when you
  finish working for the day.
* Once you are done with your work free all resources, except when
  indicated otherwise.

  #### Lab convention for naming cloud resources

You will work together with the other students in the same AWS
account, and you will see the cloud resources created by other
students. To avoid chaos it is therefore important that everybody
follows the same convention for naming the resources. This includes
public/private key pairs, security groups, EC2 Instances, load
balancers, etc.

We will use the following __lab naming convention:__ the name of every
cloud resource starts with the group, followed by your last name. You
can append more information if you want. For example when student
Nicollier in Group F creates an EC2 Instance "master", he names it

    GrF_Nicollier_master

Sometimes you are not allowed to use underscores (`_`) in the name, in
that case use dashes (`-`):

    GrF-Nicollier-master

#### A note on regions

By selecting a region an AWS user is able to control where in the
world his virtual machines or other cloud resources are located, for
example in a particular country or close to the majority of users.

To simplify the administration of your account we impose a limitation
in that you can only create resources in one region, _Northern
Virginia (us-east-1)_.

You can see the regions when you click on the drop-down menu in the
upper right corner. Make sure that you always have __N. Virginia__
selected.

If you select another region and create a resource, you will get the
error "You are not authorized to perform this operation".