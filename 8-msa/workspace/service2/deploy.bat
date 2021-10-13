@rem ===== 'gateway' 이것을 프로젝트명으로 바꿈, 서버에 app > gateway 디렉터리 생성

@rem ===== 1. 빌드된 jar파일을 서버에 전송
scp -i "D:\keyfile\myworkspace.pem" -r ./build/libs/service2*.jar ubuntu@ec2-52-79-96-141.ap-northeast-2.compute.amazonaws.com:/home/ubuntu/app/service2
@rem ===== 2. jar파일을 실행하는 run.sh 스크립트 파일을 서버에 전송
scp -i "D:\keyfile\myworkspace.pem" -r ./run.sh ubuntu@ec2-52-79-96-141.ap-northeast-2.compute.amazonaws.com:/home/ubuntu/app/service2
@rem ===== 3. run.sh 스크립파일을 실행가능하도록 권한부여(777 -> rwx rwx rwx)
ssh -i "D:\keyfile\myworkspace.pem" ubuntu@ec2-52-79-96-141.ap-northeast-2.compute.amazonaws.com "sudo chmod 777 /home/ubuntu/app/service2/run.sh"
@rem ===== 4. jar파일 있는 디렉터리까지 이동하고, run.sh로 기존 프로세스 죽이고 실행
ssh -i "D:\keyfile\myworkspace.pem" ubuntu@ec2-52-79-96-141.ap-northeast-2.compute.amazonaws.com "cd /home/ubuntu/app/service2; ./run.sh service2"


