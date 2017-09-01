package byit.osdp.base.common.easyui;

import java.util.List;

import byit.core.util.collect.Mapx;
import byit.core.util.collect.Mapxs;

/**
 * easyUI树形控件节点格式
 */
public class TreeNode {
	// ==============================Fields========================================
	private String id; // 菜单id
	private String text; // 菜单名称
	private State state = State.open;// 打开 或者 关闭
	private Mapx attributes = Mapxs.newMapx();//节点附加数据
	private List<TreeNode> children; // 设置子节点内容 

	// ==============================Constants========================================
	/** 节点状态，'open' 或 'closed' 在设置为'closed'的时候，当前节点的子节点将会从远程服务器加载 */
	public enum State {
		open, closed
	}

	// ==============================Methods==========================================
	// ==============================PropertyAccessors================================
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Mapx getAttributes() {
		return attributes;
	}

	public void setAttributes(Mapx attributes) {
		this.attributes = attributes;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

}
