# Database migration

In this task you will migrate the Drupal database to the new RDS database instance.

![Schema](./img/CLD_AWS_INFA.PNG)

## Task 01 - Securing current Drupal data

### [Get Bitnami MariaDb user's password](https://docs.bitnami.com/aws/faq/get-started/find-credentials/)

```bash
[INPUT]
//help : path /home/bitnami/bitnami_credentials
cat /home/bitnami/bitnami_credentials
```

```
[OUTPUT]
Welcome to the Bitnami package for Drupal

******************************************************************************
The default username and password is 'user' and 'vec2PoZB3aQ:'.
******************************************************************************

You can also use this password to access the databases and any other component the stack includes.

Please refer to https://docs.bitnami.com/ for more details.
```

### Get Database Name of Drupal

```bash
[INPUT]
//add string connection
mariadb -u root -p'vec2PoZB3aQ:' -e "SHOW databases;" 
```
```
[OUTPUT]
+--------------------+
| Database           |
+--------------------+
| bitnami_drupal     |
| information_schema |
| mysql              |
| performance_schema |
| sys                |
| test               |
+--------------------+
```

### [Dump Drupal DataBases](https://mariadb.com/kb/en/mariadb-dump/)

```bash
[INPUT]
mariadb-dump -u root -p'vec2PoZB3aQ:' --databases bitnami_drupal > dumpfile.sql
```

Output is the end of the dumpfile
```
[OUTPUT]
(33,0,'system','%module module installed.','a:1:{s:7:\"%module\";s:8:\"standard\";}',6,'','http://default/','','127.0.0.1',1709749738),
(34,0,'system','%module module installed.','a:1:{s:7:\"%module\";s:6:\"update\";}',6,'','http://default/','','127.0.0.1',1709749738),
(35,0,'cron','Cron run completed.','a:0:{}',6,'','http://default/','','127.0.0.1',1709749739);
/*!40000 ALTER TABLE `watchdog` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-03-28 14:11:17
```

### Create the new Data base on RDS

```sql
[INPUT]
mariadb -h dbi-devopsteam13.cshki92s4w5p.eu-west-3.rds.amazonaws.com \
-u admin -p'DEVOPSTEAM13!' \
-e "CREATE DATABASE bitnami_drupal;"
```

### [Import dump in RDS db-instance](https://mariadb.com/kb/en/restoring-data-from-dump-files/)

Note : you can do this from the Drupal Instance. Do not forget to set the "-h" parameter.

```sql
[INPUT]
mariadb -h dbi-devopsteam13.cshki92s4w5p.eu-west-3.rds.amazonaws.com \
-u admin -p'DEVOPSTEAM13!' < dumpfile.sql
```

```sql
[OUTPUT]
MariaDB [bitnami_drupal]> show tables;
+----------------------------------+
| Tables_in_bitnami_drupal         |
+----------------------------------+
| block_content                    |
| block_content__body              |
| block_content_field_data         |
| block_content_field_revision     |
| block_content_revision           |
| block_content_revision__body     |
| cache_bootstrap                  |
| cache_config                     |
| cache_container                  |
| cache_data                       |
| cache_default                    |
| cache_discovery                  |
| cache_dynamic_page_cache         |
| cache_entity                     |
| cache_menu                       |
| cache_page                       |
| cache_render                     |
| cache_toolbar                    |
| cachetags                        |
| comment                          |
| comment__comment_body            |
| comment_entity_statistics        |
| comment_field_data               |
| config                           |
| file_managed                     |
| file_usage                       |
| help_search_items                |
| history                          |
| key_value                        |
| menu_link_content                |
| menu_link_content_data           |
| menu_link_content_field_revision |
| menu_link_content_revision       |
| menu_tree                        |
| node                             |
| node__body                       |
| node__comment                    |
| node__field_image                |
| node__field_tags                 |
| node_access                      |
| node_field_data                  |
| node_field_revision              |
| node_revision                    |
| node_revision__body              |
| node_revision__comment           |
| node_revision__field_image       |
| node_revision__field_tags        |
| path_alias                       |
| path_alias_revision              |
| router                           |
| search_dataset                   |
| search_index                     |
| search_total                     |
| semaphore                        |
| sequences                        |
| sessions                         |
| shortcut                         |
| shortcut_field_data              |
| shortcut_set_users               |
| taxonomy_index                   |
| taxonomy_term__parent            |
| taxonomy_term_data               |
| taxonomy_term_field_data         |
| taxonomy_term_field_revision     |
| taxonomy_term_revision           |
| taxonomy_term_revision__parent   |
| user__roles                      |
| user__user_picture               |
| users                            |
| users_data                       |
| users_field_data                 |
| watchdog                         |
+----------------------------------+
```
### [Get the current Drupal connection string parameters](https://www.drupal.org/docs/8/api/database-api/database-configuration)

```bash
[INPUT]
//help : same settings.php as before
cat stack/drupal/sites/default/settings.php

[OUTPUT]
$databases['default']['default'] = array (
  'database' => 'bitnami_drupal',
  'username' => 'bn_drupal',
  'password' => '2b9defd18a354804a1d4c4742c252fb39d808c12cfc2046ffc8f31432ae8a060',
  'prefix' => '',
  'host' => '127.0.0.1',
  'port' => '3306',
  'isolation_level' => 'READ COMMITTED',
  'driver' => 'mysql',
  'namespace' => 'Drupal\\mysql\\Driver\\Database\\mysql',
  'autoload' => 'core/modules/mysql/src/Driver/Database/mysql/',
);
```

### Replace the current host with the RDS FQDN
```
[INPUT]
sudo sed -i "/\s\s'host' => '127.0.0.1',/c\ \ 'host' => 'dbi-devopsteam13.cshki92s4w5p.eu-west-3.rds.amazonaws.com'," \
    stack/drupal/sites/default/settings.php
```

```
//settings.php

$databases['default']['default'] = array (
'database' => 'bitnami_drupal',
'username' => 'bn_drupal',
'password' => '2b9defd18a354804a1d4c4742c252fb39d808c12cfc2046ffc8f31432ae8a060',
'prefix' => '',
'host' => 'dbi-devopsteam13.cshki92s4w5p.eu-west-3.rds.amazonaws.com',
'port' => '3306',
'isolation_level' => 'READ COMMITTED',
'driver' => 'mysql',
'namespace' => 'Drupal\\mysql\\Driver\\Database\\mysql',
'autoload' => 'core/modules/mysql/src/Driver/Database/mysql/',
);
```

### [Create the Drupal Users on RDS Data base](https://mariadb.com/kb/en/create-user/)

Note : only calls from both private subnets must be approved.
* [By Password](https://mariadb.com/kb/en/create-user/#identified-by-password)
* [Account Name](https://mariadb.com/kb/en/create-user/#account-names)
* [Network Mask](https://cric.grenoble.cnrs.fr/Administrateurs/Outils/CalculMasque/)

```sql
[INPUT]
CREATE USER bn_drupal@'10.0.13.0/255.255.255.240' IDENTIFIED BY '2b9defd18a354804a1d4c4742c252fb39d808c12cfc2046ffc8f31432ae8a060';

GRANT ALL PRIVILEGES ON bitnami_drupal.* TO 'bn_drupal'@'10.0.13.0/255.255.255.240' IDENTIFIED BY '2b9defd18a354804a1d4c4742c252fb39d808c12cfc2046ffc8f31432ae8a060';


//DO NOT FOREGT TO FLUSH PRIVILEGES
```

```
[OUTPUT]

+---------------------------+-------------+-------------------------------------------+
| Host                      | User        | authentication_string                     |
+---------------------------+-------------+-------------------------------------------+
| localhost                 | mariadb.sys |                                           |
| %                         | root        | *65B8C580316A16BE72F24528CE57F1436CF16E62 |
| %                         | bn_drupal   | *774097D0FF922910DD5E38A8BE4E6886FD3CA240 |
| 10.0.13.0/255.255.255.240 | bn_drupal   | *774097D0FF922910DD5E38A8BE4E6886FD3CA240 |
+---------------------------+-------------+-------------------------------------------+
```

```sql
//validation
[INPUT]
SHOW GRANTS for 'bn_drupal'@'10.0.[XX].0/[yourMask]]';

[OUTPUT]
+----------------------------------------------------------------------------------------------------------------------------------+
| Grants for bn_drupal@10.0.13.0/255.255.255.240                                                                                  |
+----------------------------------------------------------------------------------------------------------------------------------+
| GRANT USAGE ON *.* TO `bn_drupal`@`10.0.13.0/255.255.255.240` IDENTIFIED BY PASSWORD '*774097D0FF922910DD5E38A8BE4E6886FD3CA240' |
    | GRANT ALL PRIVILEGES ON `bitnami_drupal`.* TO `bn_drupal`@`10.0.13.0/255.255.255.240`                                            |
+----------------------------------------------------------------------------------------------------------------------------------+
```

### Validate access (on the drupal instance)

```sql
[INPUT]
mariadb -h dbi-devopsteam13.cshki92s4w5p.eu-west-3.rds.amazonaws.com \
-u bn_drupal -p2b9defd18a354804a1d4c4742c252fb39d808c12cfc2046ffc8f31432ae8a060 \
bitnami_drupal -e "SHOW DATABASES;"

```

```
[OUTPUT]
+--------------------+
| Database           |
+--------------------+
| bitnami_drupal     |
| information_schema |
+--------------------+
2 rows in set (0.001 sec)
```

* Repeat the procedure to enable the instance on subnet 2 to also talk to your RDS instance.

```sql
[INPUT]
mariadb -h dbi-devopsteam13.cshki92s4w5p.eu-west-3.rds.amazonaws.com \
-u admin -p'DEVOPSTEAM13!' \
-e "GRANT ALL PRIVILEGES ON bitnami_drupal.* TO 'bn_drupal'@'10.0.18.128/255.255.255.240' IDENTIFIED BY '2b9defd18a354804a1d4c4742c252fb39d808c12cfc2046ffc8f31432ae8a060';"

// Validation
mariadb -h dbi-devopsteam13.cshki92s4w5p.eu-west-3.rds.amazonaws.com \
-u bn_drupal -p2b9defd18a354804a1d4c4742c252fb39d808c12cfc2046ffc8f31432ae8a060 \
bitnami_drupal -e "SHOW DATABASES;"

```