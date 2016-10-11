# Script for SD_P04_G02 Project testing

#read username
username="sd0402"

while :
do
	i=0
	j=0
	k=0

	# Menu Options
	echo ""
	echo ""
	echo "SD_P04G02 Shell Script"
	echo ""
	echo "Select an Option"
	echo " * 1 : Deploy Files to Hosts"
	echo " * 2 : Run Project"
	echo " * 3 : Remove Files from Hosts"
	echo " * 4 : Check Deploy"
	echo " * 5 : Exit"
	echo ""

	# Read Input
	read SEL

	case $SEL in
		1) 	echo 'Deploy Files to Hosts';
		   	if [ -f "hosts.txt" ] && [ `wc -l hosts.txt | awk '{print $1}'` -gt 7 ]
			then
				# Remove previous deploy informations
				if [ -f 'deploy.txt' ]
				then
					rm deploy.txt
				fi
				for dest in $(<hosts.txt); do
					if [ -n "$(ping -w 1 -c 1 ${dest})" ]
					then
						if [ $i -eq 0 ]
						then
							scp -r SD_T3_P04_G02.jar ${username}@${dest}:/home/sd0402/Public/classes/ >/dev/null
						fi
						echo 'index' $i
						scp -r SD_T3_P04_G02.jar java.policy ${username}@${dest}:/home/sd0402/Project3/ >/dev/null
					  	echo 'Copied to computer ' ${dest}
					  	echo ${dest} >> deploy.txt
					  	(( i+=1 ))
					  	sleep 1
					else
						echo '${dest} is not ready'
					fi
					if [ $i -eq 8 ]
					then
						break
					fi
				done
			else
				echo "Not enough machines in hosts.txt!"
			fi;;

		2) 	echo 'Run Project';
			if [ -f "deploy.txt" ] && [ `wc -l deploy.txt | awk '{print $1}'` -eq 8 ]
			then
				for machine in $(<deploy.txt); do
					myDeploys[$j]=${machine} # store machine
					(( j+=1 ))
				done
				for port in $(<ports.txt); do
					myPorts[$k]=${port} # store machine
					(( k+=1 ))
				done
				echo -n "Enter the Log Name : "
				read log_name
				wait
				declare -a arrE=(	"registry.ServerRegisterRemoteObject ${myPorts[9]} localhost ${myPorts[0]}"
									"serverSide.Repository.RepositoryServer ${myPorts[1]} ${myDeploys[0]} ${myPorts[0]}"
									"serverSide.Shop.ShopServer ${myPorts[2]} ${myDeploys[0]} ${myPorts[0]}"
									"serverSide.Workshop.WorkshopServer ${myPorts[3]} ${myDeploys[0]} ${myPorts[0]}"
									"serverSide.PrimeStorage.PrimeStorageServer ${myPorts[4]} ${myDeploys[0]} ${myPorts[0]}"
									"clientSide.ClientCraftsman ${myDeploys[0]} ${myPorts[0]}"
									"clientSide.ClientCustomer ${myDeploys[0]} ${myPorts[0]}"
									"clientSide.ClientEntrepreneur ${myDeploys[0]} ${myPorts[0]} $log_name")
				# Run rmi registry service
				# rmiregistry -J-Djava.rmi.server.codebase="http://l040101-ws01.ua.pt/sd0402/classes/SD_T3_P04_G02.jar"  -J-Djava.rmi.server.useCodebaseOnly=true 22410 &
				ssh -n -f ${username}@${myDeploys[0]} "source /home/sd0402/.bash_profile; ./set_rmi_reg.sh"
				sleep 1
				# Run Servers and Clients
				for dest in $(<deploy.txt); do
					cmd="source /home/sd0402/.bash_profile;"
					cmd+="cd Project3;"
					if [ $i -lt 5 ]
					then
						cmd+=" java -cp SD_T3_P04_G02.jar -Djava.rmi.server.codebase=\"http://${myDeploys[0]}/sd0402/classes/SD_T3_P04_G02.jar\""
						cmd+=" -Djava.rmi.server.useCodebaseOnly=true -Djava.security.policy=java.policy ${arrE[$i]}"
					else
						cmd+=" java -cp SD_T3_P04_G02.jar ${arrE[$i]}"
					fi
					xterm -T ${dest} -e ssh ${username}@${dest} ${cmd} &
					if [ $i -lt 5 ]
					then
						sleep 3
					else
						sleep 0.5
					fi
					(( i+=1 ))
				done
				cmd1="source /home/sd0402/.bash_profile;"
				cmd1+="ps aux | grep sd0402 | grep rmiregistry | awk '{print \$2}' | head -1 | xargs kill -9"
				ssh -o StrictHostKeyChecking=no ${username}@${dest} ${cmd1} &
				wait
				sleep 1
				scp ${username}@${myDeploys[1]}:/home/sd0402/Project3/${log_name} .
				gedit ${log_name}
				scp ${username}@${myDeploys[1]}:/home/sd0402/Project3/logSorted .
				gedit logSorted
			else
				echo "Make Deployment first!"
			fi;;

		3) 	echo 'Remove Files from Hosts';
			cmd="rm -r Project3/*"
			cmd2="rm -r Public/classes/*"
			for dest in $(<deploy.txt); do
				if [ $i -eq 0 ]
				then
					ssh ${username}@${dest} ${cmd2} &
				fi
				echo 'index' $i
				ssh ${username}@${dest} ${cmd} &
			  	echo 'Deleted computer ' ${dest}
			  	(( i+=1 ))
			  	sleep 1
			done
			wait
			rm deploy.txt;;

		4)  echo "Check Deploy";
			if [ -f "deploy.txt" ]
			then
				for machine in $(<deploy.txt); do
					echo "Machine " $j ${machine} # store machine
					(( j+=1 ))
				done
			else
				echo "Make Deployment first!"
			fi;;
		5) 	echo 'Exit'; exit ;;
	esac
done
echo "Done"
