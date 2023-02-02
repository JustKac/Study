library(readxl)
library(dplyr)
sp_beaches <- read_excel("Desktop/Projeto_Estatistica/sp_beaches.xlsx")

sp_beaches <- filter(sp_beaches,City=="ITANHAÉM") %>%
  mutate_at(c('Enterococcus'), as.numeric) %>%
  group_by(Beach)


View(sp_beaches)
