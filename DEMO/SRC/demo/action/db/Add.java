package demo.action.db;

import java.util.List;

import net.java.mega.action.api.AbstractAction;
import net.java.mega.action.api.Message;
import net.java.mega.action.api.Validator;
import net.java.sjtools.logging.Log;
import net.java.sjtools.logging.LogFactory;
import net.java.sjtools.util.NumberUtil;
import net.java.sjtools.util.TextUtil;
import demo.bean.Record;

public class Add extends AbstractAction implements Validator {
	private static Log log = LogFactory.getLog(Add.class);

	private String id = null;
	private String nome = null;
	private boolean admin = false;
	private List xpto = null;

	public void onLoad() {
		log.info("onLoad()");
	}

	public String getId() {
		return id;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void insert() {
		log.info("insert()");
		
		log.info("XPTO = " + TextUtil.toString(getXpto()));

		RecordList recordList = (RecordList) getAction(RecordList.class);

		Record record = new Record(Integer.parseInt(id), nome, admin);

		if (recordList.getRecordList().contains(record)) {
			addMessage(new Message("add.error.duplicated"));
		} else {
			recordList.addRecord(record);

			gotoAction(recordList);
		}
	}

	public boolean isInputValid(String methodName) {
		log.info("isInputValid(" + methodName + ")");

		boolean ret = true;

		if (TextUtil.isEmptyString(id)) {
			addMessage("id", new Message("add.id.null"));
			ret = false;
		} else if (!NumberUtil.isValidInteger(id)) {
			addMessage("id", new Message("add.id.invalid"));
			ret = false;
		}

		if (TextUtil.isEmptyString(nome)) {
			addMessage("nome", new Message("add.nome.null"));
			ret = false;
		}

		return ret;
	}

	public List getXpto() {
		return xpto;
	}

	public void setXpto(List xpto) {
		this.xpto = xpto;
	}
}
