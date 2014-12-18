package org.opentosca.csarrepo.model.repository;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.opentosca.csarrepo.model.Csar;
import org.opentosca.csarrepo.model.CsarFile;
import org.opentosca.csarrepo.model.HashedFile;

public class JAXBExample {
	public static void main(String[] args) {

		Csar csar = new Csar();
		csar.setId(1);
		csar.setName("csar1 name");

		HashedFile hash = new HashedFile();
		hash.setFileName("theOnlyOne.zip");
		hash.setHash("#1337");
		hash.setId(1);
		hash.setSize(1337L);

		for (int i = 0; i < 5; i++) {
			CsarFile csarFile = new CsarFile();
			csarFile.setName("CsarFile No." + i);
			csarFile.setId(i);
			csarFile.setHashedFile(hash);
			csar.getCsarFiles().add(csarFile);
		}

		try {

			JAXBContext jaxbContext = JAXBContext.newInstance(Csar.class, CsarFile.class, HashedFile.class);

			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(csar, System.out);

		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}
}
