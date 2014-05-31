package com.hy.core.cache;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * LRU（最近最久未使用）的缓存
 * @author huangy
 * @date   2013-4-5
 */
public class LRUCache {
	private Map<String,Node> m_map=new ConcurrentHashMap<String, Node>();
	private int m_count=0;
	private int m_maxSize=100;
	private Object m_oLock=new Object();
	private final Node m_head=new Node();//双向链表节点，头部
	private final Node m_tail=new Node();//双向链表节点，尾部
	private String m_name;//输出日志用的
	private static Logger logger=LoggerFactory.getLogger(LRUCache.class);
	
	
	
	public int getMaxSize() {
		return m_maxSize;
	}


	public void setMaxSize(int maxSize) {
		this.m_maxSize = maxSize;
	}


	public LRUCache(String cacheName,int maxSize){
		m_head.setNext(m_tail);
		m_tail.setPre(m_head);
		m_maxSize=maxSize;
		m_name=cacheName;
	}

	
	public void put(String key,Object value){
		synchronized (m_oLock) {
			if(m_count==m_maxSize){
				logger.debug("["+m_name+"]缓存满了");
				cleanOld();
			}
			Node node=new Node();
			node.setKey(key);
			node.setValue(value);
			m_head.getNext().setPre(node);
			node.setNext(m_head.getNext());
			m_head.setNext(node);
			node.setPre(m_head);
			m_map.put(key, node);
			m_count++;
		}
	}
	public int size(){
		return m_count;
	}
	
	private void cleanOld() {
		Node oldNode=m_tail.getPre();
		m_map.remove(oldNode.getKey());
		oldNode.getPre().setNext(m_tail);
		m_tail.setPre(oldNode.getPre());
		m_count--;
		logger.debug("["+m_name+"]清除旧数据 : key="+oldNode.getKey()+" , value="+oldNode.getValue());
	}


	public Object get(String key){
		synchronized (m_oLock) {
			Node node=m_map.get(key);
			Object value=null;
			if(node!=null){
				moveToHead(node);
				value=node.getValue();
			}
			return value;
		}
	}
	
	public void cleanAll(){
		synchronized (m_oLock) {
			m_map.clear();
			m_head.setNext(m_tail);
			m_tail.setPre(m_head);
		}
	}
	
	private void moveToHead(Node node) {
		Node n=node;
		if(node!=m_head.getNext() && node!=null){
			//设置原点的连接关系
			n.getPre().setNext(n.getNext());
			n.getNext().setPre(n.getPre());
			//把当前节点放到最前面
			n.setPre(m_head);
			n.setNext(m_head.getNext());
			m_head.getNext().setPre(n);
			m_head.setNext(n);
			logger.debug("["+m_name+"]移动到最前面 : key="+n.getKey()+" , value="+n.getValue());
		}
	}

//	public void testAll(){
//		//测试用的，遍历所有数据
//		Node node=m_head;
//		while(node.getNext()!=m_tail){
//			node=node.getNext();
//			System.out.println(String.format("key=%s, value=%s\n", node.getKey(),node.getValue()));
//		}
//	}
	/**
	 * 双向链表节点
	 * @author huangy
	 * @date   2013-4-13
	 */
	class Node{
		private Node m_pre;
		private Node m_next;
		private String m_key;
		private Object m_value;
		
		public void setKey(String key){
			m_key=key;
		}
		public String getKey(){
			return m_key;
		}
		
		public void setValue(Object value){
			m_value=value;
		}
		public Object getValue(){
			return m_value;
		}
		
		public void setNext(Node next){
			m_next=next;
		}
		
		public Node getNext(){
			return m_next;
		}
		
		public void setPre(Node pre){
			m_pre=pre;
		}
		
		public Node getPre(){
			return m_pre;
		}
	}
}

