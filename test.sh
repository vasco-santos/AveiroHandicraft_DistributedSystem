rmiregistry -J-Djava.rmi.server.codebase="http://l040101-ws01.ua.pt/sd0402/classes/SD_T3_P04_G02.jar" \
			-J-Djava.rmi.server.useCodebaseOnly=true 22410


java -cp SD_T3_P04_G02.jar  -Djava.rmi.server.codebase="http://l040101-ws01.ua.pt/sd0402/classes/SD_T3_P04_G02.jar" \
      -Djava.rmi.server.useCodebaseOnly=true \
      -Djava.security.policy=java.policy \
      registry.ServerRegisterRemoteObject 22419 "localhost" 22410

--------------------------------------------------------------------------------------------------------------------

java -cp SD_T3_P04_G02.jar -Djava.rmi.server.codebase="http://l040101-ws01.ua.pt/sd0402/classes/SD_T3_P04_G02.jar" \
	-Djava.rmi.server.uCodebaseOnly=true \
	-Djava.security.policy=java.policy \
	serverSide.Repository.RepositoryServer 22411 "l040101-ws01.ua.pt" 22410

--------------------------------------------------------------------------------------------------------------------

java -cp SD_T3_P04_G02.jar -Djava.rmi.server.codebase="http://l040101-ws01.ua.pt/sd0402/classes/SD_T3_P04_G02.jar" \
	-Djava.rmi.server.useCodebaseOnly=true \
	-Djava.security.policy=java.policy \
	serverSide.Shop.ShopServer 22412 "l040101-ws01.ua.pt" 22410

--------------------------------------------------------------------------------------------------------------------

java -cp SD_T3_P04_G02.jar -Djava.rmi.server.codebase="http://l040101-ws01.ua.pt/sd0402/classes/SD_T3_P04_G02.jar" \
	-Djava.rmi.server.useCodebaseOnly=true \
	-Djava.security.policy=java.policy \
	serverSide.Workshop.WorkshopServer 22413 "l040101-ws01.ua.pt" 22410

-------------------------------------------------------------------------------------------------------------------

java -cp SD_T3_P04_G02.jar -Djava.rmi.server.codebase="http://l040101-ws01.ua.pt/sd0402/classes/SD_T3_P04_G02.jar" \
	-Djava.rmi.server.useCodebaseOnly=true \
	-Djava.security.policy=java.policy \
	serverSide.PrimeStorage.PrimeStorageServer 22414 "l040101-ws01.ua.pt" 22410

-------------------------------------------------------------------------------------------------------------------

java -cp SD_T3_P04_G02.jar clientSide.ClientEntrepreneur "l040101-ws01.ua.pt" 22410 "log"

--------------------------------------------------------------------------------------------------------------------

java -cp SD_T3_P04_G02.jar clientSide.ClientCustomer "l040101-ws01.ua.pt" 22410

--------------------------------------------------------------------------------------------------------------------

java -cp SD_T3_P04_G02.jar clientSide.ClientCraftsman "l040101-ws01.ua.pt" 22410