package ticketrank;

public class TicketDTO {
	private int count; //영화댓글수
	private int total; //영화 평점의 합
	
	public TicketDTO() {}

	public TicketDTO(int count, int total) {
		super();
		this.count = count;
		this.total = total;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "TicektDTO [count=" + count + ", total=" + total + "]";
	}
	
	
}
