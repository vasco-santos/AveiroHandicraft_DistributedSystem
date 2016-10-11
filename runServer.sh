xterm -e java -cp /opt/genclass.jar:SD_T3_P04_G02.jar \
	-Djava.rmi.server.codebase="http://127.0.0.1/~vsantos/SD_T3_P04_G02.jar" \
	-Djava.rmi.server.useCodebaseOnly=true \
	-Djava.security.policy=java.policy \
	serverSide.Repository.RepositoryServer 1099 "localhost" 2222 &

sleep 2

xterm -e java -cp /opt/genclass.jar:SD_T3_P04_G02.jar \
	-Djava.rmi.server.codebase="http://127.0.0.1/~vsantos/SD_T3_P04_G02.jar" \
	-Djava.rmi.server.useCodebaseOnly=true \
	-Djava.security.policy=java.policy \
	serverSide.Shop.ShopServer 1100 "localhost" 2222 &

sleep 2

xterm -e java -cp /opt/genclass.jar:SD_T3_P04_G02.jar \
	-Djava.rmi.server.codebase="http://127.0.0.1/~vsantos/SD_T3_P04_G02.jar" \
	-Djava.rmi.server.useCodebaseOnly=true \
	-Djava.security.policy=java.policy \
	serverSide.Workshop.WorkshopServer 1101 "localhost" 2222 &

sleep 2

xterm -e java -cp /opt/genclass.jar:SD_T3_P04_G02.jar \
	-Djava.rmi.server.codebase="http://127.0.0.1/~vsantos/SD_T3_P04_G02.jar" \
	-Djava.rmi.server.useCodebaseOnly=true \
	-Djava.security.policy=java.policy \
	serverSide.PrimeStorage.PrimeStorageServer 1102 "localhost" 2222  &
