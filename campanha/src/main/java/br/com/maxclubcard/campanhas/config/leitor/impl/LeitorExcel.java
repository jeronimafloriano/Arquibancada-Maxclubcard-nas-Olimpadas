package br.com.maxclubcard.campanhas.config.leitor.impl;

import br.com.maxclubcard.campanhas.config.leitor.LeitorArquivo;
import br.com.maxclubcard.campanhas.shared.exceptions.InvalidArgumentException;
import br.com.maxclubcard.campanhas.shared.exceptions.ValidationMessage;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.dhatim.fastexcel.reader.Cell;
import org.dhatim.fastexcel.reader.CellType;
import org.dhatim.fastexcel.reader.ReadableWorkbook;
import org.dhatim.fastexcel.reader.Row;
import org.dhatim.fastexcel.reader.Sheet;

public class LeitorExcel implements LeitorArquivo {

  @Override
  public Collection<List<String>> lerArquivo(String caminhoArquivo) {
    Map<Integer, List<String>> data = new HashMap<>();

    try (FileInputStream file = new FileInputStream(
        caminhoArquivo); ReadableWorkbook wb = new ReadableWorkbook(file)) {
      Sheet sheet = wb.getFirstSheet();

      try (Stream<Row> rows = sheet.openStream()) {
        rows.skip(2).forEach(r -> {
          data.put(r.getRowNum(), new ArrayList<>());

          for (Cell cell : r) {
            String valorCelula = cell.getRawValue();
            String formatoData = "dd/MM/yyyy";

            if (valorCelula.matches("\u00A0*(\\d{2}/\\d{2})")) {
              dataComCaractereVazio(data, r, valorCelula, formatoData);
            } else if (cell.getType().equals(CellType.NUMBER)) {
              dataFormatoNumber(data, r, valorCelula, formatoData);
            } else {
              data.get(r.getRowNum()).add(valorCelula);
            }
          }
        });
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return data.values();
  }

  private void dataFormatoNumber(Map<Integer, List<String>> data, Row r, String valorCelula,
      String formatoData) {
    try {
      long numero = Long.parseLong(valorCelula);
      LocalDate dataFormatada = LocalDate.of(1900, 1, 1).plusDays(numero - 2);
      String dataFormatadaString = this.formatarData(formatoData, dataFormatada);
      data.get(r.getRowNum()).add(dataFormatadaString);
    } catch (NumberFormatException e) {
      throw new InvalidArgumentException(ValidationMessage.DATA_INVALIDA);
    }
  }

  private void dataComCaractereVazio(Map<Integer, List<String>> data, Row r, String valorCelula,
      String formatoData) {
    String valorCelulaSemEspaco = "01/" + valorCelula.replaceAll("\u00A0", "");
    LocalDate dataEntrada = LocalDate.parse(valorCelulaSemEspaco, DateTimeFormatter.ofPattern("dd/MM/yy"));
    String dataFormatadaString = this.formatarData(formatoData, dataEntrada);
    data.get(r.getRowNum()).add(dataFormatadaString);
  }

  public String formatarData(String formatoData, LocalDate data) {
    DateTimeFormatter formatoSaida = DateTimeFormatter.ofPattern(formatoData);
    return data.format(formatoSaida);
  }
}
