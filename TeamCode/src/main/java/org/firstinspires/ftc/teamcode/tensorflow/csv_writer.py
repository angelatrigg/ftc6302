#Written by: Nathan Pritchett

import os
import argparse

#Arguments to allow for folder specification
parser = argparse.ArgumentParser(description="Just an example",
                                 formatter_class=argparse.ArgumentDefaultsHelpFormatter)
parser.add_argument("-f", "--folder", type=str, help="specify folder to use")
parser.add_argument("-f2", "--folder2", type=str, help="specify second folder")
parser.add_argument("-f3", "--folder3", type=str, help="specify third folder")
parser.add_argument("-f4", "--folder4", type=str, help="specify fourth folder")
parser.add_argument("-f5", "--folder5", type=str, help="specify fifth folder")
args = parser.parse_args()
config = vars(args)

#Clears csv file if the file has already been generated
file_csv = open("tftraining.csv", "w")
file_csv.write("")

#Function to determine the number of leading zeros required for the filename
def zero_number(frame_number_int):
    if frame_number_int < 10:
        frame_number_str = "0000"
    elif frame_number_int >= 10 and frame_number_int < 100:
          frame_number_str = "000"
    elif frame_number_int >= 100 and frame_number_int < 1000:
          frame_number_str = "00"
    elif frame_number_int >= 1000 and frame_number_int < 10000:
          frame_number_str = "0"
    else:
       frame_number_str = ""
    return frame_number_str



#Adds files that end in .txt to an array using folders specified by arguments
dir_list = []
for x in os.listdir(args.folder):
      if x.endswith(".txt"):
          dir_list.append(x)

dir_list_2 = []
for x in os.listdir(args.folder2):
      if x.endswith(".txt"):
          dir_list_2.append(x)

dir_list_3 = []
for x in os.listdir(args.folder3):
      if x.endswith(".txt"):
          dir_list_3.append(x)

dir_list_4 = []
for x in os.listdir(args.folder4):
      if x.endswith(".txt"):
          dir_list_4.append(x)

dir_list_5 = []
for x in os.listdir(args.folder5):
      if x.endswith(".txt"):
          dir_list_5.append(x)

#Sets the initial integer for the filename
frame_number = 00000

#Calls leading zero function at the top
frame_number_str = zero_number(frame_number)

#Creates a string that includes the folder directory and file name
file_name = "%s/%s%d.txt" % (args.folder, frame_number_str, frame_number)
file_name_png = "%s/%s%d.png" % (args.folder, frame_number_str, frame_number)

#Defines variables for eighty and ten percent of the total files
total = 0
for x in dir_list:
    total += 1
eighty_percent = round(total*0.8)
ten_percent = round(total*0.1)

#Sets "number_assigned" variables for different types of training data for equal splits
number_assigned_train = 0
number_assigned_validate = 0
number_assigned_test = 0

#Repeats as many times as there are files in the specified folder (amount of elements in array)
for x in dir_list:

       #Opens file specified earlier to be read
       file = open(file_name, "r")

       #Adds each line from the file as an element to an array
       coords = file.read().splitlines()
       file.close()

       #Deletes every 6th element in the array because it is unneeded
       del coords[::6]

       #Assigns array elements to variables
       x_min = coords[0]
       y_min = coords[1]
       x_max = coords[2]
       y_max = coords[3]
       label = coords[4]
       x_float_min = float(x_min)
       y_float_min = float(y_min)
       x_float_max = float(x_max)
       y_float_max = float(y_max)
       x_relative_min = "{:.2f}".format(x_float_min / 1280)
       y_relative_min = "{:.2f}".format(y_float_min / 720)
       x_relative_max = "{:.2f}".format(x_float_max / 1280)
       y_relative_max = "{:.2f}".format(y_float_max / 720)

       #Opens the csv file to be written to and writes variables that were just defined
       file_csv = open("tftraining.csv", "a")
      
       #Uses variables from earlier to determine how training data is split up
       if number_assigned_train < eighty_percent:
          file_csv.write("TRAIN,")
          number_assigned_train += 1
       elif number_assigned_validate < ten_percent:
            file_csv.write("VALIDATION,")
            number_assigned_validate += 1
       elif number_assigned_test < ten_percent:
            file_csv.write("TEST,")

       file_csv.write(file_name_png)
       file_csv.write(",")
       file_csv.write(label)
       file_csv.write(",")
       file_csv.write(x_relative_min)
       file_csv.write(",")
       file_csv.write(y_relative_min)
       file_csv.write(",,,")
       file_csv.write(x_relative_max)
       file_csv.write(",")
       file_csv.write(y_relative_max)
       file_csv.write(",,\n")
       file_csv.close()

       #Adds to the frame number to work through each file
       frame_number += 10

       #Calls leading zero function
       frame_number_str = zero_number(frame_number)

       #Redefines variables for file locations
       file_name = "%s/%s%d.txt" % (args.folder, frame_number_str, frame_number)
       file_name_png = "%s/%s%d.png" % (args.folder, frame_number_str, frame_number)

#Repeats process for every other folder defined

frame_number = 00000
frame_number_str = zero_number(frame_number)
file_name = "%s/%s%d.txt" % (args.folder2, frame_number_str, frame_number)
file_name_png = "%s/%s%d.png" % (args.folder2, frame_number_str, frame_number)

total = 0
for x in dir_list_2:
    total += 1
eighty_percent = round(total*0.8)
ten_percent = round(total*0.1)

number_assigned_train = 0
number_assigned_validate = 0
number_assigned_test = 0

for x in dir_list_2:

       #Opens file specified earlier to be read
       file = open(file_name, "r")

       #Adds each line from the file as an element to an array
       coords = file.read().splitlines()
       file.close()

       #Deletes every 6th element in the array because it is unneeded
       del coords[::6]

       #Assigns array elements to variables
       x_min = coords[0]
       y_min = coords[1]
       x_max = coords[2]
       y_max = coords[3]
       label = coords[4]
       x_float_min = float(x_min)
       y_float_min = float(y_min)
       x_float_max = float(x_max)
       y_float_max = float(y_max)
       x_relative_min = "{:.2f}".format(x_float_min / 1280)
       y_relative_min = "{:.2f}".format(y_float_min / 720)
       x_relative_max = "{:.2f}".format(x_float_max / 1280)
       y_relative_max = "{:.2f}".format(y_float_max / 720)

       #Opens the csv file to be written to and writes variables that were just defined
       file_csv = open("tftraining.csv", "a")
       
       #Uses variables from earlier to determine how training data is split up
       if number_assigned_train < eighty_percent:
          file_csv.write("TRAIN,")
          number_assigned_train += 1
       elif number_assigned_validate < ten_percent:
            file_csv.write("VALIDATION,")
            number_assigned_validate += 1
       elif number_assigned_test < ten_percent:
            file_csv.write("TEST,")

       file_csv.write(file_name_png)
       file_csv.write(",")
       file_csv.write(label)
       file_csv.write(",")
       file_csv.write(x_relative_min)
       file_csv.write(",")
       file_csv.write(y_relative_min)
       file_csv.write(",,,")
       file_csv.write(x_relative_max)
       file_csv.write(",")
       file_csv.write(y_relative_max)
       file_csv.write(",,\n")
       file_csv.close()

       #Adds to the frame number to work through each file
       frame_number += 10

       #Calls leading zero function
       frame_number_str = zero_number(frame_number)

       #Redefines variables for file locations
       file_name = "%s/%s%d.txt" % (args.folder2, frame_number_str, frame_number)
       file_name_png = "%s/%s%d.png" % (args.folder2, frame_number_str, frame_number)
    
frame_number = 00000
frame_number_str = zero_number(frame_number)
file_name = "%s/%s%d.txt" % (args.folder3, frame_number_str, frame_number)
file_name_png = "%s/%s%d.png" % (args.folder3, frame_number_str, frame_number)

total = 0
for x in dir_list_3:
    total += 1
eighty_percent = round(total*0.8)
ten_percent = round(total*0.1)

number_assigned_train = 0
number_assigned_validate = 0
number_assigned_test = 0

for x in dir_list_3:

       #Opens file specified earlier to be read
       file = open(file_name, "r")

       #Adds each line from the file as an element to an array
       coords = file.read().splitlines()
       file.close()

       #Deletes every 6th element in the array because it is unneeded
       del coords[::6]

       #Assigns array elements to variables
       x_min = coords[0]
       y_min = coords[1]
       x_max = coords[2]
       y_max = coords[3]
       label = coords[4]
       x_float_min = float(x_min)
       y_float_min = float(y_min)
       x_float_max = float(x_max)
       y_float_max = float(y_max)
       x_relative_min = "{:.2f}".format(x_float_min / 1280)
       y_relative_min = "{:.2f}".format(y_float_min / 720)
       x_relative_max = "{:.2f}".format(x_float_max / 1280)
       y_relative_max = "{:.2f}".format(y_float_max / 720)

       #Opens the csv file to be written to and writes variables that were just defined
       file_csv = open("tftraining.csv", "a")
       
       #Uses variables from earlier to determine how training data is split up
       if number_assigned_train < eighty_percent:
          file_csv.write("TRAIN,")
          number_assigned_train += 1
       elif number_assigned_validate < ten_percent:
            file_csv.write("VALIDATION,")
            number_assigned_validate += 1
       elif number_assigned_test < ten_percent:
            file_csv.write("TEST,")

       file_csv.write(file_name_png)
       file_csv.write(",")
       file_csv.write(label)
       file_csv.write(",")
       file_csv.write(x_relative_min)
       file_csv.write(",")
       file_csv.write(y_relative_min)
       file_csv.write(",,,")
       file_csv.write(x_relative_max)
       file_csv.write(",")
       file_csv.write(y_relative_max)
       file_csv.write(",,\n")
       file_csv.close()

       #Adds to the frame number to work through each file
       frame_number += 10

       #Calls leading zero function
       frame_number_str = zero_number(frame_number)

       #Redefines variables for file locations
       file_name = "%s/%s%d.txt" % (args.folder3, frame_number_str, frame_number)
       file_name_png = "%s/%s%d.png" % (args.folder3, frame_number_str, frame_number)
    
frame_number = 00000
frame_number_str = zero_number(frame_number)
file_name = "%s/%s%d.txt" % (args.folder4, frame_number_str, frame_number)
file_name_png = "%s/%s%d.png" % (args.folder4, frame_number_str, frame_number)

total = 0
for x in dir_list_4:
    total += 1
eighty_percent = round(total*0.8)
ten_percent = round(total*0.1)

number_assigned_train = 0
number_assigned_validate = 0
number_assigned_test = 0

for x in dir_list_4:

       #Opens file specified earlier to be read
       file = open(file_name, "r")

       #Adds each line from the file as an element to an array
       coords = file.read().splitlines()
       file.close()

       #Deletes every 6th element in the array because it is unneeded
       del coords[::6]

       #Assigns array elements to variables
       x_min = coords[0]
       y_min = coords[1]
       x_max = coords[2]
       y_max = coords[3]
       label = coords[4]
       x_float_min = float(x_min)
       y_float_min = float(y_min)
       x_float_max = float(x_max)
       y_float_max = float(y_max)
       x_relative_min = "{:.2f}".format(x_float_min / 1280)
       y_relative_min = "{:.2f}".format(y_float_min / 720)
       x_relative_max = "{:.2f}".format(x_float_max / 1280)
       y_relative_max = "{:.2f}".format(y_float_max / 720)

       #Opens the csv file to be written to and writes variables that were just defined
       file_csv = open("tftraining.csv", "a")
       
       #Uses variables from earlier to determine how training data is split up
       if number_assigned_train < eighty_percent:
          file_csv.write("TRAIN,")
          number_assigned_train += 1
       elif number_assigned_validate < ten_percent:
            file_csv.write("VALIDATION,")
            number_assigned_validate += 1
       elif number_assigned_test < ten_percent:
            file_csv.write("TEST,")

       file_csv.write(file_name_png)
       file_csv.write(",")
       file_csv.write(label)
       file_csv.write(",")
       file_csv.write(x_relative_min)
       file_csv.write(",")
       file_csv.write(y_relative_min)
       file_csv.write(",,,")
       file_csv.write(x_relative_max)
       file_csv.write(",")
       file_csv.write(y_relative_max)
       file_csv.write(",,\n")
       file_csv.close()

       #Adds to the frame number to work through each file
       frame_number += 10

       #Calls leading zero function
       frame_number_str = zero_number(frame_number)

       #Redefines variables for file locations
       file_name = "%s/%s%d.txt" % (args.folder4, frame_number_str, frame_number)
       file_name_png = "%s/%s%d.png" % (args.folder4, frame_number_str, frame_number)
    
frame_number = 00000
frame_number_str = zero_number(frame_number)
file_name = "%s/%s%d.txt" % (args.folder5, frame_number_str, frame_number)
file_name_png = "%s/%s%d.png" % (args.folder5, frame_number_str, frame_number)

total = 0
for x in dir_list_5:
    total += 1
eighty_percent = round(total*0.8)
ten_percent = round(total*0.1)

number_assigned_train = 0
number_assigned_validate = 0
number_assigned_test = 0

for x in dir_list_5:

       #Opens file specified earlier to be read
       file = open(file_name, "r")

       #Adds each line from the file as an element to an array
       coords = file.read().splitlines()
       file.close()

       #Deletes every 6th element in the array because it is unneeded
       del coords[::6]

       #Assigns array elements to variables
       x_min = coords[0]
       y_min = coords[1]
       x_max = coords[2]
       y_max = coords[3]
       label = coords[4]
       x_float_min = float(x_min)
       y_float_min = float(y_min)
       x_float_max = float(x_max)
       y_float_max = float(y_max)
       x_relative_min = "{:.2f}".format(x_float_min / 1280)
       y_relative_min = "{:.2f}".format(y_float_min / 720)
       x_relative_max = "{:.2f}".format(x_float_max / 1280)
       y_relative_max = "{:.2f}".format(y_float_max / 720)

       #Opens the csv file to be written to and writes variables that were just defined
       file_csv = open("tftraining.csv", "a")
       
       #Uses variables from earlier to determine how training data is split up
       if number_assigned_train < eighty_percent:
          file_csv.write("TRAIN,")
          number_assigned_train += 1
       elif number_assigned_validate < ten_percent:
            file_csv.write("VALIDATION,")
            number_assigned_validate += 1
       elif number_assigned_test < ten_percent:
            file_csv.write("TEST,")

       file_csv.write(file_name_png)
       file_csv.write(",")
       file_csv.write(label)
       file_csv.write(",")
       file_csv.write(x_relative_min)
       file_csv.write(",")
       file_csv.write(y_relative_min)
       file_csv.write(",,,")
       file_csv.write(x_relative_max)
       file_csv.write(",")
       file_csv.write(y_relative_max)
       file_csv.write(",,\n")
       file_csv.close()

       #Adds to the frame number to work through each file
       frame_number += 10

       #Calls leading zero function
       frame_number_str = zero_number(frame_number)

       #Redefines variables for file locations
       file_name = "%s/%s%d.txt" % (args.folder5, frame_number_str, frame_number)
       file_name_png = "%s/%s%d.png" % (args.folder5, frame_number_str, frame_number)
    
print("\n\nWriting complete!\n\n")
