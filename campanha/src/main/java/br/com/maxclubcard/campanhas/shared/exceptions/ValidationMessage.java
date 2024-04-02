package br.com.maxclubcard.campanhas.shared.exceptions;

public enum ValidationMessage {

  CLIENTE_NAO_ENCONTRADO("0001", "Cliente não encontrado."),
  ID_CAMPANHA_OBRIGATORIO("0002", "É obrigatório informar o ID da campanha."),
  CLIENTE_OBRIGATORIO("0003", "É obrigatório informar os dados do cliente."),
  CAMPANHA_NAO_ENCONTRADA("0004", "Campanha não encontrada."),
  CARTAO_OBRIGATORIO("0005", "É obrigatório informar os dados do cartão."),
  DATA_INVALIDA("0007", "Formato de data inválido para conversão."),
  NOME_OBRIGATORIO("0008", "É obrigatório informar o nome."),
  VALOR_MINIMO_OBRIGATORIO("0009", "É obrigatório informar o valor mínimo da campanha."),
  CAMPANHA_OBRIGATORIA("00010", "É obrigatório informar a campanha."),
  CPF_OBRIGATORIO("00011", "É obrigatório informar o cpf do cliente."),
  NUMERO_CARTAO_OBRIGATORIO("00012", "É obrigatório informar o número do cartão."),
  TIPO_OBRIGATORIO("00013", "É obrigatório informar o tipo do cartão(Crédito/Débito)."),
  BANDEIRA_OBRIGATORIA("00014", "É obrigatório informar a bandeira do cartão."),
  DATA_EXPIRACAO_OBRIGATORIA("00015", "É obrigatório informar a data de expiração do cartão."),
  ID_CLIENTE_OBRIGATORIO("00016", "É obrigatório informar o ID do cliente."),
  DATA_NASCIMENTO_OBRIGATORIA("00017", "É obrigatório informar a data de nascimento do cliente."),
  SEXO_OBRIGATORIO("00018", "É obrigatório informar o sexo do cliente."),
  EMAIL_OBRIGATORIO("00019", "É obrigatório informar o email do cliente."),
  EMAIL_INVALIDO("00020", "O email informado é inválido"),
  CELULAR_OBRIGATORIO("00021", "É obrigatório informar o numero de celular."),
  DADOS_SORTEIO_OBRIGATORIO("00022", "É obrigatório informar os dados de participação do sorteio"),
  NUMERO_DA_SORTE_OBRIGATORIO("00023",
      "É obrigatório a geração de um número da sorte para participação em sorteio"),
  NOME_CAMPANHA_JA_EXISTE("00024", "Já existe uma campanha cadastrada com esse nome.");


  private final String code;
  private final String message;

  ValidationMessage(String code, String message) {
    this.code = code;
    this.message = message;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

}
