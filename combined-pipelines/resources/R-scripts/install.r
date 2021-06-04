tryInstall = function(pkg) {
  # stop if the package cant be installed
  tryCatch(expr={ install.packages(pkg) }, warning=stop)
}

print("Installing necessary packages...")
if (!require(tm, quietly=TRUE)){ tryInstall("tm") }
if (!require(stringr, quietly=TRUE)){ tryInstall("stringr") }
if (!require(stopwords, quietly=TRUE, warn.conflicts=FALSE)){ tryInstall("stopwords") }
if (!require(slam, quietly=TRUE)){ tryInstall("slam") }
if (!require(snakecase, quietly=TRUE)){ tryInstall("snakecase") }
if (!require(data.table, quietly=TRUE, warn.conflicts=FALSE)){ tryInstall("data.table") }
if (!require(XML, quietly=TRUE)){ tryInstall("XML") }
if (!require(SnowballC, quietly=TRUE)){ tryInstall("SnowballC") }
