library(readxl)
library(dplyr)
library(ggplot2)
library(scales)

# Lê os arquivos da planilha
sp_beaches <- read_excel("/Users/caioh/vscode-workspace/Estudos/study/Projeto_Estatistica/sp_beaches.xlsx", sheet = 1, col_names = TRUE)

# Cidade a mim atribuida para a elaboração do projeto
my_city_is <- "ITANHAÉM"

# Filtra os dados da cidade a mim atribuida para análise,
# converte os dados da coluna "Enterococcus" para número
# e agrupa os dados por praia.
my_data <- sp_beaches %>% filter(City==my_city_is) %>%
            mutate_at(c('Enterococcus'), as.numeric) %>%
               group_by(Beach)

# Visualização dos dados filtrados e agrupados
View(my_data)

# Encontra os valores média, desvio-padrão, mediana, Q1, Q3,
# mínimo e máximo dos Enterococcus de cada praia
dados_estatisticos <- summarise(my_data, media = mean(Enterococcus), # Encontra a média
                              desvio_padrao = sd(Enterococcus), # Encontra o desvio padrão
                              mediana = median(Enterococcus), # Encontra a mediana
                              Q1 = quantile(Enterococcus, 0.25), # Encontra o 1° quartil
                              Q3 = quantile(Enterococcus, 0.75), # Encontra o 3° quartil
                              minimo = min(Enterococcus), # Encontra o mínimo
                              maximo = max(Enterococcus)) # Encontra o máximo

# Visualização dos dados estatísticos obtidos
View(dados_estatisticos)


# Gráfico de Barras de máximos por praia
ggplot(dados_estatisticos, aes(x = reorder(Beach, -media), y = media, fill = Beach)) +
        geom_bar(stat = "identity", width = 0.5, position = "dodge") + 
        geom_text(aes(y = media, label = paste0(round(media / sum(media) * 100, 1), "%")),
                  data = dados_estatisticos, 
                  col = "#619CFF", vjust = -0.5) +
        labs(title = "Gráfico de máximos por praia",
             subtitle = "Fonte: Base de Dados Disponibiizada pelo professor") +
        xlab("Beach") +
        ylab("Maximo") +
        theme_minimal()


#Gráfico de Pizza de máximos por praia
ggplot(dados_estatisticos, aes(x = "", y = maximo, fill = Beach)) +
  geom_bar(width = 1, stat = "identity") + 
  coord_polar("y", start = 0, direction = -1) + 
  geom_text(data = dados_estatisticos, 
            aes(x ="", y=maximo, label = paste0(round(maximo / sum(maximo) * 100, 1), "%")),
            position = position_stack(vjust = 0.5), size = 2) +
  labs(title = "Gráfico de máximos por praia",
       subtitle = "Fonte: Base de Dados Disponibiizada pelo professor")
        theme_minimal()
        
# Histograma de Enterococcus por praia
ggplot(my_data, aes(x = Enterococcus)) +
        geom_histogram(fill='blue', color ='black') +
        xlab("Beach") +
        ylab("Enterococcus") +
        labs(title = "Histograma de Enterococcus por praia",
             subtitle = "Fonte: Base de Dados Disponibiizada pelo professor") +
        theme_minimal()


# Box Plot de Enterococcus por praia
ggplot(my_data, aes(x = Beach, y = Enterococcus), fill = Beach) +
        geom_boxplot(color = "black", fill = "blue") +
        xlab("Data") +
        ylab("Value") +
        theme_bw() + 
        theme(panel.grid.major = element_blank(), panel.grid.minor = element_blank()) +
        ggtitle("Box Plot de Enterococcus por praia") +
        theme_minimal()
