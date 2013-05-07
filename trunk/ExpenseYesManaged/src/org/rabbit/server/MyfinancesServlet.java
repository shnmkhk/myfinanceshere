package org.rabbit.server;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rabbit.model.Transaction;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;

public class MyfinancesServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();

		Query q = pm.newQuery(Transaction.class);
		try {
			List<Transaction> results = (List<Transaction>) q.execute();
			resp.getWriter().println("Results: " + results.size());
			System.out.println("Results: " + results.size() + " Time: "
					+ new Date());
			for (Transaction t : results) {
				resp.getWriter().println(t.toString());
				resp.getWriter().println("<br/>");
			}
		} finally {
			q.closeAll();
			pm.close();
		}
		resp.setContentType("text/html");
		resp.getWriter().println("<a href='/'>Click to add another entry</a>");

	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		resp.setContentType("text/html");
		resp.getWriter().println("Hello, world");

		String skip = req.getParameter("skip");
		PersistenceManager pm;
		if (skip == null) {
			System.out.println("Adding an entry -->");
			pm = PMF.get().getPersistenceManager();
			try {

				Transaction transaction = new Transaction();

				transaction.setDescription(req.getParameter("description"));
				transaction.setOpeningBalance(Double.parseDouble(req
						.getParameter("openingBalance")));
				transaction.setTransactionAmount(Double.parseDouble(req
						.getParameter("transactionAmount")));

				pm.makePersistent(transaction);
			} finally {
				pm.close();
			}

			DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

			int i = 0;
			while (!pm.isClosed() || i < 3) {
				System.out.println("pm.isClosed(): " + pm.isClosed() + " i: "
						+ i);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				i++;
			}
		}
		resp.getWriter().println(
				"<a href='/myfinances'>Click to see the listing</a>");
	}
}
