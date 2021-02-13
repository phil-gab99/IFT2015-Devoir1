REM Script used to convert eps files in the res directory to pdf files

FOR /F "tokens=1 delims=." %%a IN ('dir res /b ^| find ".eps"') DO epstopdf res/%%a.eps --output res/%%a.pdf