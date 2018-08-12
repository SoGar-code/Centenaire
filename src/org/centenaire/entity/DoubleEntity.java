package org.centenaire.entity;

/**
 * Class providing 'Double Entity'.
 * 
 * <p>This class inherits from Entity in order to
 * use a regular 'DropTable'. However, it is not
 * an 'Entity' class properly speaking, but rather 
 * an auxiliary class to implement labeled relations.</p>
 * 
 * <p>It is not a full 'Entity' class in the sense that
 * it has neither a DAO, nor a functioning index, for instance.</p>
 *
 * @see org.centenaire.util.dragndrop.DropTable
 */
public class DoubleEntity<T extends Entity, U> extends Entity {
	private T objT;
	private U objU;
	
	public DoubleEntity(T objT, U objU) {
		super(objT.getIndex());
		this.objT = objT;
		this.objU = objU;
	}

	public T getObjT() {
		return objT;
	}

	public void setObjT(T objT) {
		this.objT = objT;
	}

	public U getObjU() {
		return objU;
	}

	public void setObjU(U objU) {
		this.objU = objU;
	}

	@Override
	public Object getEntry(int i) {
		switch (i){
		case 0:
			return objT;
		case 1:
			return objU;
		default:
			return "-";
		}
	}

	@Override
	public void setEntry(int i, Object obj) {
		switch (i){
		case 0:
			this.objT=(T) obj;
			break;
		case 1:
			this.objU=(U) obj;
			break;
		default:
			;
		}
	}

}
