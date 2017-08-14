::#!
@echo off
call scala %0 %*
goto :eof
::!#

println("Hello, "+args(0)+"!")