#function 1 - it is used to load a sample training data
fun1<-function(x){
idx <- sample(1:nrow(iris), as.integer(x * nrow(iris)))
return (idx)
}
#function 2 - test the KNN algorithm
fun2<-function(x,y,z){
#loading library
library(class)
#loading dataset
data(iris)
#loading training object thought function 1. "X" = % of dataset used for the training
idx <-fun1(x)
#loading the KNN where "Y" = columns and "Z" = number of neighbours considered
tr <- iris[idx, ]
ts <- iris[-idx, ]
preds <- knn(tr[, -y], ts[, -y], tr[, y], k = z)
#return the value
return (preds)
}
#main
#X = 70%
#Y = 5 (Class is the last column)
#Z = 3 neighbours considered
ejecution<-fun2(0.7,5,3)
table(ejecution)

