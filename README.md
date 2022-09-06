Микросервис Заказа

Сборка и установка в minikube
1) `gradle build`
2) `docker build -t gurok/arch_order .`
3) `docker push gurok/arch_order`
4) `kubectl create namespace arch-gur`
5) `helm install arch-order ./deployment/app/`
   `kubectl get pods -n arch-gur`
6) `helm install gorelov-redis ./deployment/redis/`

---

При локальном запуске profileId для заказов берется контроллером из requestParams. 
В prod режиме контроллер принимает токен авторизации из хедеров.

Для локального запуска redis: `docker-compose up`
Указать в Idea: `--spring.profiles.active=local,hw06`

Для установки redis manager
`winget install qishibo.AnotherRedisDesktopManager`

Port-forward БД:
`kubectl port-forward -n arch-gur arch-auth-postgresql-deployment-0 5433:5432`
`kubectl port-forward -n arch-gur redis-ss-0 6379:6379`

---
### Очистка пространства:

- `helm uninstall arch-order`
- `helm uninstall gorelov-redis`
- `kubectl delete namespace arch-gur`

