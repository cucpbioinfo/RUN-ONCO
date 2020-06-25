library(GenVisR)

fun1<-function(x){
set.seed(383)
pdf(file=x, width=12, height=8)
waterfall(brcaMAF, mainRecurCutoff = 0.06)
dev.off()
}