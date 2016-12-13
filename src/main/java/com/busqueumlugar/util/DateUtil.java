package com.busqueumlugar.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

public class DateUtil extends GregorianCalendar {
	
  public static final long serialVersionUID = 1;

  /** Retorna a data no formato dd/MM/yyyy */            
  public static final int DMN_DATA      = 1;
  
  /** Retorna a hora no formato hh:mm */            
  public static final int DMN_HORA      = 2;
  
  /** Retorna a hora no formato hh:mm:ss */            
  public static final int DMN_HORA_SEG  = 3;
  
  /** Retorna a data no formato dd/MM/yyyy hh:mm:ss */            
  public static final int DMN_DATA_COMPL = 4;
  
  /** Retorna a data no formato dd/MM/yyyy hh:mm */            
  public static final int DMN_DATA_HORA = 5;
  
  /** Retorna a hora no formato HH:mm a (isto é, exibindo am ou pm)*/  
  public static final int DMN_HORA_AM_PM = 6;
  
  /** Retorna a data no formato ddMMyyyyhhmmss */
  public static final int DMN_DATA_COMPL_CONCAT = 7;
  
  /** Retorna a data no formato MM/yyyy */
  public static final int DMN_MES_ANO = 8;
  
  /** Retorna a data no formato yyyyMM */
  public static final int DMN_ANO_MES_CONCAT = 9;
  
  /** Retorna a data no formato ddMMyyyy */
  public static final int DMN_DATA_CONCAT	= 10;
  
  /** Retorna a data no formato hhmmss */
  public static final int DMN_HORA_CONCAT	= 11;
  
  /** Retorna a data no formato yyyyMMdd */
  public static final int DMN_ANO_MES_DIA_CONCAT	= 12;
  
  private String strData = "";
  private boolean showString = false;

    /**
     * Inicializa a classe
     */
    public DateUtil() {
      super();
    }

   /**
    * Inicializa o DateUtil passando um objeto java.sql.Date
    * @param pDateSql Data no formato do banco
    */
    public DateUtil(java.sql.Date pDateSql){
        java.util.Date ldataUtil = new java.util.Date(pDateSql.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        strData = sdf.format(ldataUtil);
        this.setTime(ldataUtil);
    }
    
   /**
    * Inicializa o DateUtil passando um objeto java.sql.Date
    * @param pTimestamp Data no formato do banco
    */
    public DateUtil(Timestamp pTimestamp){
        java.util.Date ldataUtil = new java.util.Date(pTimestamp.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        strData = sdf.format(ldataUtil);
        this.setTime(ldataUtil);
    }
    
   /**
    * Este construtor recebe tres inteiros representando dia, mes e ano. A partir desses  
    * dados cria uma instancia do objeto Calendar que encpasulará uma data representada   
    * pelos dados passados. O formato desejado para apresentar a data para o usuário é    
    * determinado pelo segundo parâmetro. 
    * @param dia dia
    * @param mes mes
    * @param ano ano
    */
    public DateUtil(int dia, int mes, int ano) {
      super();

      ano = ano -1900;

      this.set(Calendar.DAY_OF_MONTH, dia);
      this.set(Calendar.MONTH, mes-1);
      this.set(Calendar.YEAR, ano + 1900);
      //Para não ter problemas na diferença de dias
      // Por default é incluido a hora corrente
      this.set(Calendar.HOUR_OF_DAY, 0);
      this.set(Calendar.MINUTE, 0);
      this.set(Calendar.SECOND, 0);
      this.set(Calendar.MILLISECOND, 0);
    }
    
   /**
    * Este construtor recebe tres inteiros representando dia, mes e ano. A partir desses  
    * dados cria uma instancia do objeto Calendar que encpasulará uma data representada   
    * pelos dados passados. O formato desejado para apresentar a data para o usuário é    
    * determinado pelo segundo parâmetro. 
    * @param strDia dia
    * @param strMes mes
    * @param strAno ano
    */
    public DateUtil(String strDia, String strMes, String strAno) {
      super();
      
      int dia = strDia != null ? Integer.parseInt(strDia) :  0;
      int mes = strMes != null ? Integer.parseInt(strMes) :  0;
      int ano = strAno != null ? Integer.parseInt(strAno) :  0;
      
      ano = ano -1900;
      
      this.set(Calendar.DAY_OF_MONTH, dia);
      this.set(Calendar.MONTH, mes-1);
      this.set(Calendar.YEAR, ano + 1900);
      //Para não ter problemas na diferença de dias
      // Por default é incluido a hora corrente
      this.set(Calendar.HOUR_OF_DAY, 0);
      this.set(Calendar.MINUTE, 0);
      this.set(Calendar.SECOND, 0);
      this.set(Calendar.MILLISECOND, 0);
    }
    
  /**
    * Este construtor recebe dois inteiros representando mes e ano. A partir desses  
    * dados cria uma instancia do objeto Calendar que encpasulará uma data representada   
    * pelos dados passados. O formato desejado para apresentar a data para o usuário é    
    * determinado pelo segundo parâmetro. 
    * @param mes mes
    * @param ano ano
    */
    public DateUtil(int mes, int ano) {
      super();
      
      ano = ano -1900;
      
      this.set(Calendar.DAY_OF_MONTH, 1);
      this.set(Calendar.MONTH, mes);
      this.set(Calendar.YEAR, ano + 1900);
      //Para não ter problemas na diferença de dias
      // Por default é incluido a hora corrente
      this.set(Calendar.HOUR_OF_DAY, 0);
      this.set(Calendar.MINUTE, 0);
      this.set(Calendar.SECOND, 0);
      this.set(Calendar.MILLISECOND, 0);
    }

  /**
   * Este construtor recebe seis inteiros representando dia, mes, ano, hora, minuto e segundo.           
   * A partir desses dados cria uma instancia do objeto Calendar que encpasulará uma data representada   
   * pelos dados passados. O formato desejado para apresentar a data para o usuário é                    
   * determinado pelo segundo parâmetro. 
   * @param dia dia
   * @param mes mes
   * @param ano ano
   * @param hora hora
   * @param min  minuto
   * @param seg  segundo
   */
    public DateUtil(int dia, int mes, int ano, int hora, int min, int seg) {
      super();

      ano = ano -1900;

      this.set(Calendar.DAY_OF_MONTH, dia);
      this.set(Calendar.MONTH, mes);
      this.set(Calendar.YEAR, ano + 1900);
      this.set(Calendar.HOUR_OF_DAY, hora);
      this.set(Calendar.MINUTE, min);
      this.set(Calendar.SECOND, seg);
      this.set(Calendar.MILLISECOND, 0);
    }

  /**
   * Este construtor recebe uma String no formato dd.mm.yyyy e instancia um objeto       
   * Calendar que encpasulará uma data representada pela String passada. O formato       
   * desejado para apresentar a data para o usuário é determinado pelo segundo parâmetro. 
   * @param pData data
   */
    public DateUtil(String pData) {
        super();

        try{
          String lStrData;
          
          SimpleDateFormat formatter = new SimpleDateFormat ("dd/MM/yyyy hh:mm:ss");
          if (pData.indexOf("/") == -1){
            SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S");
            lStrData = formatter.format(form.parse(pData));
          }else{
            if (pData.indexOf(":") == -1){
              pData = pData + " 00:00:00";
            }
            lStrData = pData;
          }
          java.util.Date lDatData = formatter.parse(lStrData);

          this.setTime(lDatData);
        }catch (ParseException e){
            System.err.println("Erro Parse DateUtil: "+e.getMessage());
            throw new RuntimeException(e);
        }
    }


  /**
   * Este construtor recebe uma String no formato dd.mm.yyyy, uma segunda String        
   * no formato hh.mm.ss e instancia um objeto Calendar que encpasulará uma data         
   * representada pelas duas Strings passadas. O formato desejado para apresentar a data 
   * para o usuário é determinado pelo segundo parâmetro.
   * @param pData data
   * @param pHora hora
   */
    public DateUtil(String pData, String pHora) {
      super();

      StringTokenizer tokenData = new StringTokenizer(pData, "/");
      int dia = Integer.parseInt(tokenData.nextToken());
      int mes = Integer.parseInt(tokenData.nextToken());
      int ano = Integer.parseInt(tokenData.nextToken()) - 1900;

      StringTokenizer tokenHora = new StringTokenizer(pHora, ":");
      int hora= Integer.parseInt(tokenHora.nextToken());
      int min = Integer.parseInt(tokenHora.nextToken());
      int seg = Integer.parseInt(tokenHora.nextToken());

      this.set(Calendar.DAY_OF_MONTH, dia);
      this.set(Calendar.MONTH, mes);
      this.set(Calendar.YEAR, ano + 1900);
      this.set(Calendar.HOUR_OF_DAY, hora);
      this.set(Calendar.MINUTE, min);
      this.set(Calendar.SECOND, seg);
      this.set(Calendar.MILLISECOND, 0);
    }
    
  /**
   * Retorna o intervalo de dias entre o DateUtil
   * corrente e a DateUtil passado como parâmetro
   * @param data DateUtil
   * @return o intervalo de dias entre data 
   *         inicializa e data passada como parâmetro
   */
   public int daysBetween(DateUtil data){
       return toJulian() - data.toJulian();
   }
    

    /**
     * Calcula o número de dias entre esta data e uma anterior.
     * @param calendarIni Uma data anterior a ser calculada
     * @return O número de dias entre as datas
     */
    public int getDayRange(DateUtil calendarIni){
       long dataIniMilis = calendarIni.getTime().getTime();
       long dataFimMilis = this.getTime().getTime();

       long lLngRange = dataFimMilis - dataIniMilis;

       return (int)(lLngRange / (1000 * 60 * 60 * 24));
    }

   /**
    * Troca formato de mês (número)
    * para data string (ex.: Jan, Fev)
    * recebendo o formato de DateUtil
    * @return data string (ex.: Jan, Fev)
    */
    public String monthToStr(){
        String strMesNum = "";
        int lIntMes = this.get(DateUtil.MONTH);
        
        if (lIntMes == 0) {
            strMesNum = "Jan";
        }
        else if(lIntMes == 1) {
            strMesNum = "Fev";
        }
        else if(lIntMes == 2) {
            strMesNum = "Mar";
        }
        else if(lIntMes == 3) {
            strMesNum = "Abr";
        }
        else if(lIntMes == 4) {
            strMesNum = "Mai";
        }
        else if(lIntMes == 5) {
            strMesNum = "Jun";
        }
        else if(lIntMes == 6) {
            strMesNum = "Jul";
        }
        else if(lIntMes == 7) {
            strMesNum = "Ago";
        }
        else if(lIntMes == 8) {
            strMesNum = "Set";
        }
        else if(lIntMes == 9) {
            strMesNum = "Out";
        }
        else if(lIntMes == 10) {
            strMesNum = "Nov";
        }
        else if(lIntMes == 11) {
            strMesNum = "Dez";
        }
        return strMesNum;
    }

   /**
    * Troca formato de mês (número)
    * para data string (ex.: Janeiro, Fevereiro, etc...)
    * recebendo o formato de DateUtil
    * @return data string (ex.: Janeiro, Fevereiro, etc...)
    */
    public String monthToStrExt(){
        String strMesNum = "";
        int lIntMes = this.get(DateUtil.MONTH);
        
        if(lIntMes == 0) {
            strMesNum = "Janeiro";
        }
        else if(lIntMes == 1) {
            strMesNum = "Fevereiro";
        }
        else if(lIntMes == 2) {
            strMesNum = "Março";
        }
        else if(lIntMes == 3) {
            strMesNum = "Abril";
        }
        else if(lIntMes == 4) {
            strMesNum = "Maio";
        }
        else if(lIntMes == 5) {
            strMesNum = "Junho";
        }
        else if(lIntMes == 6) { 
            strMesNum = "Julho";
        }
        else if(lIntMes == 7) {
            strMesNum = "Agosto";
        }
        else if(lIntMes == 8) {
            strMesNum = "Setembro";
        }
        else if(lIntMes == 9) {
            strMesNum = "Outubro";
        }
        else if(lIntMes == 10) {
            strMesNum = "Novembro";
        }
        else if(lIntMes == 11) {
            strMesNum = "Dezembro";
        }
        return strMesNum;
    }

   
  /**
   * Troca formato de data (mes/ano - mmm/yy ex.:Jan/2000)
   * para data normal (mes/ano - mm/yyyy ex.: 01/2000)
   * @param strMes mes
   * @return data normal (mes/ano - mm/yyyy ex.: 01/2000)
   */
  public static String monthToInt(String strMes){
    String strMesNum = "";
    
    if(strMes.equals("Jan")){ strMesNum = "01";}
    else if(strMes.equals("Fev")){ strMesNum = "02";}
    else if(strMes.equals("Mar")){ strMesNum = "03";}
    else if(strMes.equals("Abr")){ strMesNum = "04";}
    else if(strMes.equals("Mai")){ strMesNum = "05";}
    else if(strMes.equals("Jun")){ strMesNum = "06";}
    else if(strMes.equals("Jul")){ strMesNum = "07";}
    else if(strMes.equals("Ago")){ strMesNum = "08";}
    else if(strMes.equals("Set")){ strMesNum = "09";}
    else if(strMes.equals("Out")){ strMesNum = "10";}
    else if(strMes.equals("Nov")){ strMesNum = "11";}
    else if(strMes.equals("Dez")){ strMesNum = "12";}
    
    return strMesNum;
  }
  
  /**
   * Clona esta data
   * @return clone
   */
  public Object clone() {
      return super.clone();
  }

    /**
     * Retorna data juliana
     * @return data juliana
     */
    private int toJulian()  {  
       int jy = this.get(DateUtil.YEAR);
       
       if (this.get(DateUtil.YEAR) < 0) {
            jy++;
       } 
       int jm = this.get(DateUtil.MONTH);

       if (this.get(DateUtil.MONTH) > 2) {
            jm++;
       } 
       else {  
            jy--;
            jm += 13;
       }
       int jul = (int) (java.lang.Math.floor(365.25 * jy)
       + java.lang.Math.floor(30.6001*jm) + this.get(DateUtil.DAY_OF_MONTH) + 1720995.0);

       int greg = 15 + 31*(10+12*1582);

       if (this.get(DateUtil.DAY_OF_MONTH) + 31 * (this.get(DateUtil.MONTH) + 12 * this.get(DateUtil.YEAR)) >= greg) { 
            int ja = (int)(0.01 * jy);
            jul += 2 - ja + (int)(0.25 * ja);
       }
       return jul;
    }

    /**
     * Recupera o dia da semana
     * @return O dia da semana
     */
    public int getDayOfWeek(){
      return this.get(DateUtil.DAY_OF_WEEK);
    }

    /**
     * Recupera o dia da semana por extenso
     * @return O dia da semana por extenso
     */
    public String getNameDayOfWeek(){
      int lIntDayOfWeek = this.get(DateUtil.DAY_OF_WEEK);
      String lStrDayOfWeek = "";
      switch (lIntDayOfWeek) {
        case DateUtil.SUNDAY:   lStrDayOfWeek = "Domingo";
                                break;
        case DateUtil.MONDAY:   lStrDayOfWeek = "Segunda";
                                break;
        case DateUtil.TUESDAY:  lStrDayOfWeek = "Terça";
                                break;
        case DateUtil.WEDNESDAY:lStrDayOfWeek = "Quarta";
                                break;
        case DateUtil.THURSDAY: lStrDayOfWeek = "Quinta";
                                break;
        case DateUtil.FRIDAY:   lStrDayOfWeek = "Sexta";
                                break;
        case DateUtil.SATURDAY: lStrDayOfWeek = "Sábado";
                                break;
      }
      return lStrDayOfWeek;
    }

    /**
     * Recupera o dia no mês corrente
     * @return O dia no mês corrente
     */
    public int getDayOfMonth(){
      return this.get(DateUtil.DAY_OF_MONTH);
    }

    /**
     * Recupera o mês no ano corrente
     * @return O mês no ano corrente
     */
    public int getMonth(){
      return this.get(DateUtil.MONTH) + 1;
    }

    /**
     * Recupera o ano corrente
     * @return O ano corrente
     */
    public int getYear(){
      return this.get(DateUtil.YEAR);
    }


    /**
     * Recupera a hora corrente
     * @return O mês no ano corrente
     */
    public int getHour(){
      return this.get(DateUtil.HOUR);
    }


    /**
     * Recupera o minuto corrente
     * @return O mês no ano corrente
     */
    public int getMinute(){
      return this.get(DateUtil.MINUTE);
    }


    /**
     * Recupera o segundo corrente
     * @return O mês no ano corrente
     */
    public int getSecond(){
      return this.get(DateUtil.SECOND);
    }

    /**
     * Transforma o DateUtil em um objeto do tipo TimeStamp
     * @return Timestamp a partir do DateUtil
     */
    public Timestamp toTimeStamp(){
       long dataMilis = this.getTime().getTime();

       return new Timestamp(dataMilis);
    }

    /**
     * Retorna data formatada no formato dd/MM/yyyy
     * @return data formatada no formato dd/MM/yyyy
     */
    public String getStrDate(){
        SimpleDateFormat formatter = new SimpleDateFormat ("dd/MM/yyyy");
        return formatter.format(this.getTime());
    }

    /**
     * Retorna a hora
     * @return a hora
     */
    public String getStrTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm");
        return formatter.format(this.getTime());
    }

    /**
     * Retorna a data formatada de acordo com o parâmetro
     * @param   pIntTipo formato da data
     * @return  data formatada de acordo com o parâmetro
     */
    public String getDate(int pIntTipo){
        String lStrMascara = "";
        switch(pIntTipo) {
            case DMN_DATA:
                    lStrMascara = "dd/MM/yyyy";
                    break;
            case DMN_HORA:
                    lStrMascara = "hh:mm";
                    break;
            case DMN_HORA_SEG:
                    lStrMascara = "hh:mm:ss";
                    break;
            case DMN_DATA_COMPL:
                    lStrMascara = "dd/MM/yyyy hh:mm:ss";
                    break;
            case DMN_DATA_HORA:
                    lStrMascara = "dd/MM/yyyy hh:mm";
                    break;
            case DMN_HORA_AM_PM:
                    lStrMascara = "HH:mm a";
                    break;
            case DMN_DATA_COMPL_CONCAT:
                    lStrMascara = "ddMMyyyyhhmmss";
                    break;
            case DMN_MES_ANO:
                    lStrMascara = "MM/yyyy";
                    break;
            case DMN_ANO_MES_CONCAT:
                    lStrMascara = "yyyyMM";
                    break;
            case DMN_DATA_CONCAT:
	                lStrMascara = "ddMMyyyy";
	                break;
            case DMN_HORA_CONCAT:
	                lStrMascara = "hhmmss";
	                break;
            case DMN_ANO_MES_DIA_CONCAT:
            	    lStrMascara = "yyyyMMdd";
            		break;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(lStrMascara);
        
        return formatter.format(this.getTime());
    }
    
    /**
     * Retorna a data como String
     * @return a data como String
     */
    public String toString() {
      if(!showString) {
          return this.getDate(DateUtil.DMN_DATA_COMPL);
      } 
      else {
          return strData;
      }
    }

   /**
    * Retorna a data atual + pIntNumDias, esse método verifica se a soma
    * cai no Final de Semana e adiciona dias a ela, para que so seja válido os
    * dias úteis da semana.
    * @param pIntNumDias numero de dias
    * @return a data atual + o numero de dias
    */
   public DateUtil getBusinessPlus(int pIntNumDias) {
     DateUtil date = (DateUtil)this.clone();
     
     for (int i=1; i<=pIntNumDias; i++){
        date.set(DateUtil.DAY_OF_YEAR, date.get(DateUtil.DAY_OF_YEAR)+1);
        
        if (date.getDayOfWeek() == DateUtil.SUNDAY){  // Se for igual a domingo
            date.set(DateUtil.DAY_OF_YEAR, date.get(DateUtil.DAY_OF_YEAR)+1);
        } 
        else if (date.getDayOfWeek() == DateUtil.SATURDAY){   // Se for igual a sábado
            date.set(DateUtil.DAY_OF_YEAR, date.get(DateUtil.DAY_OF_YEAR)+2);
        }
      }
      return date;
    }
   
   /**
    * Soma determinada quantidade de dias (passado como parêmetro) à data. 
    * */
   public DateUtil somaDias(int pIntNumDias) {
	   DateUtil date = (DateUtil)this.clone();
	     
	   for (int i=1; i<=pIntNumDias; i++){
		   date.set(DateUtil.DAY_OF_YEAR, date.get(DateUtil.DAY_OF_YEAR)+1);	        
	   }
	   return date;
	}

   /**
    * Adianta a data no número de dias passado por parâmetro.
    * @param pIntNumDias Número de dias a ser adiantado
    */
    public void getDatePlus(int pIntNumDias) {
      this.set(DateUtil.DAY_OF_YEAR, this.get(DateUtil.DAY_OF_YEAR)+pIntNumDias);
    }
    
  /**
   * Retrocede a data no número de dias passado por parâmetro.
   * @param pIntNumDias Número de dias a ser retrocedido
   */
    public void getDateMinus(int pIntNumDias) {
      this.set(DateUtil.DAY_OF_YEAR, this.get(DateUtil.DAY_OF_YEAR)-pIntNumDias);
    }
    
    /**
    * Adianta a data no número de meses passado por parâmetro.
    * @param pIntNumMeses Número de meses a ser adiantado
    */
    public void getMonthPlus(int pIntNumMeses) {
      this.set(DateUtil.MONTH, this.get(DateUtil.MONTH)+pIntNumMeses);
    }
    
    public DateUtil somaMeses(int pIntNumMeses) {
    	DateUtil date = (DateUtil)this.clone();
    	
    	date.set(DateUtil.MONTH, this.get(DateUtil.MONTH)+pIntNumMeses);
    	
        return date;
    }

    /**
     * Retrocede a data no número de meses passado por parâmetro.
     * @param pIntNumMeses Número de meses a ser adiantado
     */
    public DateUtil subtraiMeses(int pIntNumMeses) {
    	DateUtil date = (DateUtil)this.clone();
    	
    	date.set(DateUtil.MONTH, this.get(DateUtil.MONTH)-pIntNumMeses);
    	
        return date;
    }
    
  /**
   * Retorna o primeiro dia da semana da data corrente.
   * @return Primeiro dia da semana
   */
    public DateUtil getFirstDateOfWeek() {
      DateUtil firstDate = (DateUtil)this.clone();
      
      int diff = DateUtil.SUNDAY - firstDate.get(DateUtil.DAY_OF_WEEK) - 1;
      
      firstDate.add( DateUtil.DAY_OF_YEAR, diff );
      
      firstDate.set(DateUtil.HOUR, 12);
      firstDate.set(DateUtil.MINUTE, 0);
      
      return firstDate;
    }
    
    /**
     * Retorna o ultimo dia da semana da data corrente.
     * @return  Ultimo dia da semana
     */
    public DateUtil getLastDateOfWeek() {
      DateUtil lastDate = (DateUtil)this.clone();
      
      // lastDate.set(this.DAY_OF_WEEK, this.get(this.SATURDAY));
      int diff = DateUtil.SATURDAY - lastDate.get(DateUtil.DAY_OF_WEEK) - 1;
      if ( diff < 0 ){
        diff = 6 + diff;
      }
      lastDate.add( DateUtil.DAY_OF_YEAR, diff );
      
      lastDate.set(DateUtil.HOUR, 12);
      lastDate.set(DateUtil.MINUTE, 0);
      
      return lastDate;
    }
    
    /**
     * Retorna o proximo sabado
     * @return o proximo sabado
     */
    public DateUtil getNextSaturday() {
      DateUtil data = (DateUtil)this.clone();
      data.set(DateUtil.DAY_OF_WEEK, this.get(DateUtil.SATURDAY));
      data.set(DateUtil.HOUR, 12);
      data.set(DateUtil.MINUTE, 0);
      
      return data;
    }

    /**
     * Compara esta data com outra passada como parametro
     * @param when data para comparação
     * @return resultado da comparação
     */
    public int compareTo(DateUtil when) {
      if (this.before(when)) {
        return -1;
      } 
      if (this.after(when)) {
        return 1;
      }
      return 0;
    }
    
    /**
     * Retorna este objeto como sql.Date
     * @return este objeto como sql.Date
     */
    public java.sql.Date toSqlDate() {
    	String date = getDate(DMN_DATA);
    	
    	String ano = date.substring(6, date.length());
    	String mes = date.substring(3, 5);
    	String dia = date.substring(0, 2);
    	
    	date = ano + "-" + mes + "-" + dia;
    	
    	return java.sql.Date.valueOf(date);
    }
    
    /**
     * Retorna o intervalo de meses entre duas datas
     * @param   calendarIni data inicial
     * @return  o intervalo de meses entre duas datas
     */
    public int getMonthsBetween(DateUtil calendarIni)  {
        DateUtil    calendarIniTemp     = (DateUtil)calendarIni.clone();
        int         i                   = 0;
        int         lnDiaIniEscolhido   = calendarIni.get(DateUtil.DAY_OF_MONTH);
        
        while(((calendarIniTemp.get(DateUtil.YEAR)*100) + calendarIniTemp.get(DateUtil.MONTH)) <=
              ((this.get(DateUtil.YEAR)*100)+ this.get(DateUtil.MONTH)))  {
              
            int         lnMesProx   = calendarIniTemp.get(DateUtil.MONTH) + 1;
            DateUtil    lobjMesProx = new DateUtil(1, lnMesProx, calendarIni.get(DateUtil.YEAR));
            
            if (lnDiaIniEscolhido > lobjMesProx.getActualMaximum(DateUtil.DAY_OF_MONTH)) {
                calendarIniTemp.set(DateUtil.DAY_OF_MONTH, lobjMesProx.getActualMaximum(DateUtil.DAY_OF_MONTH));
            }
            else if (lnDiaIniEscolhido > calendarIni.getActualMaximum(DateUtil.DAY_OF_MONTH)) {
                calendarIniTemp.set(DateUtil.DAY_OF_MONTH, lnDiaIniEscolhido);
            }
            
            if (calendarIniTemp.get(DateUtil.MONTH) == 11) {
                calendarIniTemp.set(DateUtil.MONTH, 0);
                calendarIniTemp.set(DateUtil.YEAR, calendarIniTemp.get(DateUtil.YEAR) + 1);
            } 
            else {
                calendarIniTemp.set(DateUtil.MONTH, calendarIniTemp.get(DateUtil.MONTH) + 1);
            }
            i++;
        }
        return i;
    }

    /**
     * Retorna os dias uteis entre o DateUtil corrente
     * e um intervalo de dias passado como parâmetro
     * @param 	interval intervalo de dias
     * @return	os dias uteis entre o DateUtil corrente
     * 			e um intervalo de dias passado como parâmetro
     */
    public int getBusinessDays(int interval) {
    	int 		businessDays	= 0;
    	DateUtil	dateTemp		= (DateUtil)this.clone();
        boolean 	isNotSunday		= this.getDayOfWeek() != SUNDAY;
        boolean 	isNotSaturday	= this.getDayOfWeek() != SATURDAY; 
    	         
        if (isNotSunday && isNotSaturday) {  
        	businessDays++;
        } 
    	
		for (int i = 1; i <= interval; i++) {
			dateTemp.set(DateUtil.DAY_OF_YEAR, dateTemp.get(DateUtil.DAY_OF_YEAR) + 1);
	        
	        isNotSunday		= dateTemp.getDayOfWeek() != SUNDAY;
	        isNotSaturday	= dateTemp.getDayOfWeek() != SATURDAY; 
	        
	        if (isNotSunday && isNotSaturday) {  
	        	businessDays++;
	        } 
	    }
    	
		return businessDays;
    }
    
    
    public String getMesAno(){
		int yearNow = this.getYear();
		int ano = yearNow;
		String mes = this.monthToStr();
		String mesano = String.valueOf(mes) + "/" + String.valueOf(ano);
		return mesano;
    }
    
    public static String formataData(Date data) {
    	SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");   
    	return fmt.format(data);    	
    }
    
    public static Date formataDataBanco(String data) {
    	SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");   
    	try {
			return fmt.parse(data);
		} catch (ParseException e) {
			return null;
		}    	
    }

    
    public static boolean formatoDataValido(String data){
    	Date dataEntrada = formataDataBanco(data);
    	if (dataEntrada != null)
    		return true;
    	else
    		return false;
    }

}







