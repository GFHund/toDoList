import os

env = Environment()

newpath = os.environ.get('PATH')
env.Append(ENV={'PATH':newpath})

env.Java('classes','src')
#env.Jar(target='toDoList.jar',source=['','manifest.txt'])#classes
#env.Jar(target='toDoList.jar',source=['src/toDoList.java','META_INF/MANIFEST.MF'])