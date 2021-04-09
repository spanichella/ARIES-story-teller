print("Installing necessary packages...")
if (!require(tm, quietly=TRUE)){ install.packages("tm") }
if (!require(stringr, quietly=TRUE)){ install.packages("stringr") }
if (!require(stopwords, quietly=TRUE, warn.conflicts=FALSE)){ install.packages("stopwords") }
if (!require(slam, quietly=TRUE)){ install.packages("slam") }
if (!require(snakecase, quietly=TRUE)){ install.packages("snakecase") }
if (!require(data.table, quietly=TRUE, warn.conflicts=FALSE)){ install.packages("data.table") }
if (!require(XML, quietly=TRUE)){ install.packages("XML") }
if (!require(SnowballC, quietly=TRUE)){ install.packages("SnowballC") }
