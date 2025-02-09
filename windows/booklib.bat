@echo on
set arg=%1
set file=%arg:~14,-1%
curl -d "filename=%file%" -H "Content-Type: application/x-www-form-urlencoded" -X POST http://localhost:10000/openbook
