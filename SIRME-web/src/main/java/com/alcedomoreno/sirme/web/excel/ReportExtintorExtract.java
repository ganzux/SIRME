package com.alcedomoreno.sirme.web.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;

import com.alcedomoreno.sirme.business.data.Address;
import com.alcedomoreno.sirme.business.data.Customer;
import com.alcedomoreno.sirme.business.data.Manager;
import com.alcedomoreno.sirme.business.data.Question;
import com.alcedomoreno.sirme.business.data.QuestionGroup;
import com.alcedomoreno.sirme.business.data.Reply;
import com.alcedomoreno.sirme.business.data.ReplyGroup;
import com.alcedomoreno.sirme.business.data.Report;
import com.alcedomoreno.sirme.business.data.Team;
import com.alcedomoreno.sirme.business.data.TypeWork;
import com.alcedomoreno.sirme.business.data.Work;
import com.alcedomoreno.sirme.web.util.DateTimeUtil;

public class ReportExtintorExtract {

    private static ReportExtintorExtract instance;
    private ReportExtintorExtract(){}
    public static ReportExtintorExtract getInstance(){
        if ( instance == null )
            instance = new ReportExtintorExtract();
        return instance;
    }

    public Work getReportFromDOC(File file) throws Exception{

        Work extintorWork = new Work();
        WordExtractor extractor = null;

        String fileName = file.getName();
        String codeCustomer = fileName.split(" ")[0];
        codeCustomer = codeCustomer.trim();

        if (codeCustomer == null || codeCustomer.isEmpty()){
            throw new Exception("No cumple con el formato establecido, no hay c�digo en el nombre del fichero");
        }

        try{
            codeCustomer = codeCustomer.replaceAll("\\D+","");
            int cdo = Integer.parseInt(codeCustomer);
        } catch(Exception we){
            throw new Exception("No cumple con el formato establecido, no hay c�digo en el nombre del fichero");
        }

        Customer customer = new Customer();
        customer.setCodeCustomer(codeCustomer);
        extintorWork.setCustomer(customer);

        HWPFDocument document = new HWPFDocument(new FileInputStream(file));
        extractor = new WordExtractor(document);
        String fileData = extractor.getText();

       /* extintorReport.setNameManager( fileData.split("\r\n")[0] );
        fileData = fileData.substring( fileData.indexOf("\r\n") );

        String addresess = fileData.substring( fileData.indexOf("\r\n"), fileData.toLowerCase().indexOf("tel") );
        for ( String data:addresess.split("\r\n") )
        	if ( !data.isEmpty() )
        		extintorReport.addContact("Direccion",data);
       */
        //System.out.println(fileData);

        String[] lines = fileData.split("\r\n");

        if (lines[4].toLowerCase().contains("extintores")){

            String addressstr = lines[2].substring(lines[2].indexOf("irecci�n") + 10);
            Address address = new Address();
            address.setMainAddress(addressstr);
            address.setCustomer(customer);
            extintorWork.setAddress(address);
            extintorWork.setStatus(Work.STATUS_CERRADO);
            extintorWork.setTypeWork(new TypeWork(1));

            String fechaR = lines[3].substring(lines[3].indexOf("revisi") + 10, lines[3].indexOf("\tN� Albar"));
            try{
                extintorWork.setDate(DateTimeUtil.getInstance().getDate(fechaR.trim()));
                int year = Integer.valueOf(DateTimeUtil.getInstance().formatDate(extintorWork.getDate(), "yyyy"));
                extintorWork.setYear(year);
            } catch(Exception e){
                throw new Exception("No se puede determinar la fecha: " + fechaR);
            }

            String nAlbaran = lines[3].substring(lines[3].indexOf("lbar") + 6, lines[3].indexOf("Revisad"));
            try{
                nAlbaran = cleanString(nAlbaran);
                extintorWork.setAlbaran(Integer.parseInt(nAlbaran));
            } catch(Exception e){
                throw new Exception("Albaran " + nAlbaran + " incorrecto");
            }

            String revisadoPor = lines[3].substring(lines[3].indexOf("por") + 5);
            Team team = new Team();
            team.setNameTeam(revisadoPor);
            extintorWork.setTeam(team);

            if (lines[5].startsWith("N�")){
                throw new Exception("No cumple con el formato establecido, tiene columna N�");
            }

            java.util.List<ReplyGroup> replyGroups = new ArrayList<ReplyGroup>();
            for (int i = 7;i<lines[i].length();i++){
                String[] data = lines[i].split("\t");
                try{
                    //System.out.println("Procesando linea... " + lines[i]);
                    ReplyGroup replyGroup = new ReplyGroup();
                    QuestionGroup questionGroup = new QuestionGroup();
                    questionGroup.setIdQuestionGroup(1);
                    replyGroup.setQuestionGroup(questionGroup);

                    // UBICACI�N
                    Reply reply = new Reply();
                    reply.setReplyGroup(replyGroup);
                    reply.setReply(data[0]);
                    Question q = new Question();
                    q.setIdQuestion(3);
                    reply.setQuestion(q);

                    // EFICACIA
                    Reply reply2 = new Reply();
                    reply2.setReplyGroup(replyGroup);
                    String replytxt = data[1];
                    if (!data[2].trim().isEmpty()){
                        replytxt += ", Polvo (" + data[2] + ")";
                    } else if (!data[3].trim().isEmpty()){
                        replytxt += ", Agua (" + data[3] + ")";
                    } else if (!data[4].trim().isEmpty()){
                        replytxt += ", CO2 (" + data[4] + ")";
                    }
                    reply2.setReply(replytxt);
                    Question q2 = new Question();
                    q2.setIdQuestion(7);
                    reply2.setQuestion(q2);

                    // ID
                    Reply reply3 = new Reply();
                    reply3.setReplyGroup(replyGroup);
                    reply3.setReply(data[5]);
                    Question q3 = new Question();
                    q3.setIdQuestion(1);
                    reply3.setQuestion(q3);
                    Reply reply4 = new Reply();
                    reply4.setReplyGroup(replyGroup);
                    reply4.setReply(data[5]);
                    Question q4 = new Question();
                    q4.setIdQuestion(2);
                    reply4.setQuestion(q4);

                    // Acceso y visibilidad
                    Reply reply5 = new Reply();
                    reply5.setReplyGroup(replyGroup);
                    if (!data[6].trim().isEmpty()){
                        reply5.setReply("Bien");
                    } else {
                        reply5.setReply("Mal");
                    }
                    Question q5 = new Question();
                    q5.setIdQuestion(9);
                    reply5.setQuestion(q5);

                    // Seguros y precintos
                    Reply reply6 = new Reply();
                    reply6.setReplyGroup(replyGroup);
                    if (!data[6].trim().isEmpty()){
                        reply6.setReply("Bien");
                    } else {
                        reply6.setReply("Mal");
                    }
                    Question q6 = new Question();
                    q6.setIdQuestion(10);
                    reply6.setQuestion(q6);

                    // FABRICACION
                    Reply reply7 = new Reply();
                    reply7.setReplyGroup(replyGroup);
                    reply7.setReply(data[14]);
                    Question q7 = new Question();
                    q7.setIdQuestion(4);
                    reply7.setQuestion(q7);

                    List<Reply> replies = new ArrayList<Reply>();
                    replies.add(reply);
                    replies.add(reply2);
                    replies.add(reply3);
                    replies.add(reply4);
                    replies.add(reply5);
                    replies.add(reply6);
                    replies.add(reply7);

                    replyGroup.setReplies(replies);
                    replyGroup.setWork(extintorWork);
                    replyGroup.setNameReplyGroup("Procedente de la migraci�n");

                    replyGroups.add(replyGroup);
                } catch(Exception e){
                    //System.out.println("ERROR: " + e.getMessage());
                }
                java.util.List<Report> reports = new ArrayList<Report>();
                Report report = new Report();
                report.setIdReport(1);
                report.setReplyGroups(replyGroups);
                reports.add(report);
                extintorWork.setReports(reports);
            }


        } else {
            throw new Exception("No es un fichero de extintores: " + lines[4]);
        }


        return extintorWork;
    }

    private Manager getPhone(Manager manager, String chain){

        if ( chain.trim().toLowerCase().startsWith("tel") || chain.trim().toLowerCase().startsWith("telefono") ){
            String tlf = "";
            if ( chain.indexOf(";") != chain.lastIndexOf(";") )
                tlf = ( cleanString( chain.substring( chain.indexOf(";")+1, chain.lastIndexOf(";")-3 )) );
            else
                tlf = ( cleanString( chain.substring( chain.indexOf(";")+1 ) ) );
            manager = countNumbers( tlf, manager );
        }

        String fax = chain.substring( chain.lastIndexOf(";")+1 );
        if ( !fax.trim().isEmpty() && chain.indexOf(";") != chain.lastIndexOf(";") )
            manager.addContact("Fax", cleanString( fax ) );

        return manager;
    }

    private String getNumber(String chain){
        String ss = "";

        for (int i = 0; i < chain.length(); i++)
            if ( Character.isDigit( chain.charAt(i) ) )
                ss += chain.charAt(i);
        return ss;
    }

    private Manager countNumbers(String chain, Manager manager){
        boolean phone = false;
        String currentPhone = "";

        for (int i = 0; i < chain.length(); i++)
            if ( Character.isDigit( chain.charAt(i) ) ){
                currentPhone += chain.charAt(i);
                if ( currentPhone.length() == 9 ){
                    if ( !phone ){
                        manager.setPhoneManager( cleanString( currentPhone ) );
                        phone = true;
                    }
                    else
                        manager.addContact("Telefono", cleanString( currentPhone ) );
                    currentPhone = "";
                }
            }

        return manager;
    }

    private String cleanString(String s) {
        try{
            return new String ( s.replaceAll("\r", "").replaceAll("\n", "").replaceAll("\r\n", "").replaceAll("\\xE2", "").replaceAll("\\x80", "").replaceAll("\\x8B", "").replaceAll("mailto:", "").replaceAll("mail:", "").trim().getBytes(), "UTF-8" ).replaceAll("[^\\x20-\\x7e]", "");
        } catch ( Exception e){
            return new String ( s.replaceAll("\r", "").replaceAll("\n", "").replaceAll("\r\n", "").replaceAll("\\xE2", "").replaceAll("\\x80", "").replaceAll("\\x8B", "").replaceAll("mailto:", "").replaceAll("mail:", "").trim() ).replaceAll("[^\\x20-\\x7e]", "");
        }
    }

    public static void main(String[] args) throws FileNotFoundException, IOException{
        //File dir = new File("C:\\extintores");
        File dir = new File("C:\\a");
        for (File child : dir.listFiles())
            try{
                Work r = ReportExtintorExtract.getInstance().getReportFromDOC(child);
                child.delete();
                System.out.println(child.getName() + " - OK");
            } catch(Exception e){
                System.out.println(child.getName() + " - KO - " + e.getMessage());
            }
    }
}
