# Samba-test
Projeto de desenvolvimento de uma aplicação web que recebe arquivo de vídeo não exibível, o altera e exibe para o usuário em formato reconhecível. O arquivo pode possuir qualquer formato e o vídeo é exibido no formato .mp4

## Funcionamento do projeto
### Servidor
O servidor da aplicação está hospedado na Amazon EC2, com o serviço de armazenamento dos arquivos na Amazon S3. O EC2 se comunica diretamento com o S3 através de um plugin, o que transfoma este um volume de arquivos. Isso traz a vantagem da aplicação web não precisar se preocupar em saber para qual serviço de armazenamento irá enviar os arquivos, apenas o caminho no sistema de arquivos local.

Para evitar o "Hard coding" das informações sobre a localização das pastas aonde a aplicação web irá ler e escrever, foi criado um arquivo texto (conveniência) de configuração com os caminhos:

    input=
    ouput=
    S3Input=
    s3Output=
    apiKey=
"input" e "output" são para a localização dessas pastas no próprio EC2, "S3Input" e "S3Output" são os caminhos das pastas no bucket do S3 que devem ser fornecidos para o serviço de conversão de vídeos Zencoder junto com a sua chave de acesso "apiKey".

Os vídeos enviados são armazenados na pasta "input" e aqueles convertidos na pasta "output"

### Zencoder
O serviço possui permissão para se conectar ao S3, ler e escrever nas pastas "input" e "output". O serviço é chamado pela aplicação utilizando a API fornecida por ele.

### Aplicação web
O site consite em uma página na qual é realizado o envio do arquivo, a requisição da conversão do vídeo e a espera da conversão pelo Zencoder e outra que exibe o vídeo resultante para o usuário.
