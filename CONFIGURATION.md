# 📘 Configurations

| Propriété                                     |                                                     Description                                                      |
|:----------------------------------------------|:--------------------------------------------------------------------------------------------------------------------:|
| batch-kafka.reader.partitions                 |                                           Liste des partitions à consommer                                           |
| batch-kafka.reader.process-name               |              Nom du processus de consommation Kafka utilisé pour le suivi dans les tables Spring Batch               |
| batch-kafka.reader.poll-timeout               | Durée maximum d'attente en seconde après la consommation du dernier message avec de passer au step de transformation |
| batch-kafka.reader.topic                      |                                                  Topic à consommer                                                   |
| batch-kafka.reader.partition-offset           |               Facultatif, liste permettant de spécifier les offsets des partitions consommées (rejeu)                |
| batch-storage.writer.bucket-name              |                                           Nom du bucket GCP de sauvegarde                                            |
| batch-storage.writer.retry-write.max-attempts |                                        Nombre de tentative maximum d'écriture                                        |
| batch-storage.writer.retry-write.delay        |                               Délais en milliseconde entre chaque tentative d'écriture                               |
| batch-storage.writer.retry-write.max-delay    |                                Délais en milliseconde maximum de tentative d'écriture                                |
| batch-postgres.reader.process-name            | Nom du processus de récupération des éléments en base de données utilisé pour le suivi dans les tables Spring Batch  |
| batch-postgres.reader.fetch-size              |                                   Nombre maximum d'éléments retournés par requêtes                                   |
| batch-postgres.writer.batch-size              |                                              Taille du batch d'écriture                                              |