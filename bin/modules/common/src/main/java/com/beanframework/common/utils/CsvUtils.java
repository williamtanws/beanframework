package com.beanframework.common.utils;

import java.util.Iterator;
import java.util.List;
import org.json.CDL;
import org.json.JSONArray;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CsvUtils {

  public static StringBuilder List2Csv(List<?> resultList) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    StringBuilder resultBuilder = new StringBuilder();

    Iterator<?> itr = resultList.iterator();
    while (itr.hasNext()) {
      Object object = itr.next();
      if (object instanceof Object[]) {

        Object[] objects = (Object[]) object;

        StringBuilder rowBuilder = new StringBuilder();
        for (int i = 0; i < objects.length; i++) {
          if (i != 0) {
            rowBuilder.append(",");
          }
          rowBuilder.append("\"");
          rowBuilder.append(objects[i]);
          rowBuilder.append("\"");
        }
        resultBuilder.append(rowBuilder);
        resultBuilder.append(System.getProperty("line.separator"));

      } else if (object instanceof String) {
        String str = (String) object;
        resultBuilder.append("\"");
        resultBuilder.append(str);
        resultBuilder.append("\"");
        resultBuilder.append(System.getProperty("line.separator"));
      }
    }

    if (resultList.isEmpty() == Boolean.FALSE && resultBuilder.length() == 0) {
      String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(resultList);

      JSONArray jsonArray = new JSONArray(json);
      resultBuilder.append(CDL.toString(jsonArray));
    }

    return resultBuilder;
  }
}
