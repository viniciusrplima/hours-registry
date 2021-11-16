# Hours Registry

System to register hours of work by tasks.

### Configuration

You must create `.env` file with variables used by system. Fill variables of database and github config with correct values.

```
GITHUB_TOKEN=<github-token>
JWT_SECRET=<jwt-secret>
DATABASE_URL=<database-url>
DATABASE_USERNAME=<database-username>
DATABASE_PASSWORD=<database-password>
```

Run the command below to setup environment variables.

```bash
for env of $(cat .env); do export $env; done
```

### Ruinning

Execute command below to run the system.

```bash
mvn spring-boot:run
```