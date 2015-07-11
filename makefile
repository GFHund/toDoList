JAVAC = javac
JAR = jar
SOURCEPATH = src
CLASSPATH = .:/d/Programmieren/MinGW/msys/1.0/home/Philipp/java/javaLibrary/jdatepicker-1.3.4.jar
TODOLISTPATH = src/gfHund/toDoList
#src\gfHund\toDoList
SOURCE = $(TODOLISTPATH)/addEntityDialog.java $(TODOLISTPATH)/DateLabelFormatter.java \
$(TODOLISTPATH)/messageDialog.java $(TODOLISTPATH)/toDoEntry.java \
$(TODOLISTPATH)/toDoList.java $(TODOLISTPATH)/toDosSource.java $(TODOLISTPATH)/serverDialog.java

all:
	$(JAVAC) -d classes -sourcepath $(SOURCEPATH) -classpath $(CLASSPATH) $(SOURCE)
	$(JAR) cvfm toDoList.jar manifest.txt -C classes/ .
