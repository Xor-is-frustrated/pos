package com.increff.pos.pdf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;

import com.increff.pos.model.OrderInvoice;

public class PDFFromFOP {

	//to do
	public static void javaToXML(OrderInvoice emp) throws JAXBException {

		String FILE_NAME = "order_receipt.xml";
		
			JAXBContext context = JAXBContext.newInstance(OrderInvoice.class);
			Marshaller m = context.createMarshaller();
			
			// for pretty-print XML in JAXB
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			// Write to File
			m.marshal(emp, new File(FILE_NAME));
		
	}

	public static void generatePDF(int id) {
		try {
			File xmlfile = new File("order_receipt.xml");
			File xsltfile = new File("src\\main\\resources\\com\\increff\\pos\\style.xsl");
			File pdfDir = new File("./src/main/resources/com/increff/pos");
			pdfDir.mkdirs();
			String s = "receipt.pdf";
			File pdfFile = new File(pdfDir, s);
			System.out.println(pdfFile.getAbsolutePath());
			final FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
			FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
			OutputStream out = new FileOutputStream(pdfFile);
			out = new java.io.BufferedOutputStream(out);
			try {

				Fop fop;

				fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);

				// Setup XSLT
				TransformerFactory factory = TransformerFactory.newInstance();
				Transformer transformer = factory.newTransformer(new StreamSource(xsltfile));

				// Setup input for XSLT transformation
				Source src = new StreamSource(xmlfile);

				// Resulting SAX events (the generated FO) must be piped through to FOP
				Result res = new SAXResult(fop.getDefaultHandler());

				// Start XSLT transformation and FOP processing
				transformer.transform(src, res);

			} catch (FOPException | TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				out.close();
			}
		} catch (IOException exp) {
			exp.printStackTrace();
		}
	}
	
	public static void generateResponse(HttpServletResponse response) throws IOException {
		String pdfFileName = "receipt.pdf";
		File pdfFile = new File("src\\main\\resources\\com\\increff\\pos\\receipt.pdf");

		response.setContentType("application/pdf");
		response.addHeader("Content-Disposition", "attachment; filename=" + pdfFileName);
		response.setContentLength((int) pdfFile.length());

		FileInputStream fileInputStream = new FileInputStream(pdfFile);
		OutputStream responseOutputStream = response.getOutputStream();
		int bytes;
		while ((bytes = fileInputStream.read()) != -1) {
			responseOutputStream.write(bytes);
		}
	}

}