# 🚀 spring-batch-kafka

Repository d'exemple d'application Spring Batch avec intégration:
- Kafka
- PostgreSQL
- GCP Storage

# 📊 FLow Chart
```mermaid
flowchart TD
    B@{shape: procs, label: "Step Filtrage"} --> |Récupération des commandes| A(Kafka)
    B --> |Enregistrement des fournitures| C[(PostgreSQL)]
    D@{shape: procs, label: "Step Transformation"} --> |Récupération des fourniture| C
    D --> |Ecriture des fichiers| EA@{ shape: lin-cyl, label: "GCP Storage"}
```
