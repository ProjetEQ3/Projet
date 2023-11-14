# $1 = date since (yyyy-mm-dd)

if [ -z "$1" ]
 then
   echo "No Date supplied ex: 2011-01-01"
   exit 1
fi

echo "Ligne écrite depuis le $1"
echo "Louis: "
echo "js: "
./gitcount.sh 2055 js "$1"
echo "java: "
./gitcount.sh 2055 java "$1"
echo "Gabriel: "
echo "js: "
./gitcount.sh 216 js "$1"
./gitcount.sh Gab js "$1"
echo "java: "
./gitcount.sh 216 java "$1"
./gitcount.sh Gab java "$1"
echo "Chawki: "
echo "js: "
./gitcount.sh Chouaki js "$1"
echo "java: "
./gitcount.sh Chouaki java "$1"
echo "Samuel: "
echo "js: "
./gitcount.sh Samuel js "$1"
echo "java: "
./gitcount.sh Samuel java "$1"
echo "Zakaria: "
echo "js: "
./gitcount.sh Zaka js "$1"
echo "java: "
./gitcount.sh Zaka java "$1"