package edu.kh.todolist.service;

import java.util.List;
import java.util.Map;

import edu.kh.todolist.dto.Todo;

public interface TodoListService {

	/** 할 일 목록 조회 + 완료된 할 일 개수 
	 * @return map
	 */
	Map<String, Object> selectTodoList();

	/** 할 일 추가
	 * @param todo
	 * @return result
	 */
	int todoAdd(Todo todo);

	/** 할 일 상세조회
	 * @param todoNo
	 * @return todo
	 */
	Todo todoDetail(int todoNo);

	/** 할 일 수정
	 * @param todoNo
	 * @return
	 */
	int todoComplete(int todoNo);

	int todoUpdate(Todo todo);

	int todoDelete(int todoNo);

	String searchTitle(int todoNo);

	int getTotalCount();

	int getCompleteCount();

	List<Todo> getTodoList();

	
}
