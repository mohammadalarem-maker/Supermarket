package com.supermarket.app.data.remote

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.supermarket.app.data.local.*
import com.supermarket.app.data.models.*
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SyncRepository @Inject constructor(
    private val db: FirebaseFirestore,
    private val productDao: ProductDao,
    private val saleDao: SaleDao,
    private val customerDao: CustomerDao,
    private val purchaseDao: PurchaseDao,
    private val expenseDao: ExpenseDao
) {
    private val TAG = "SyncRepo"

    suspend fun syncAll() {
        syncProducts()
        syncSales()
        syncCustomers()
        syncPurchases()
        syncExpenses()
    }

    private suspend fun syncProducts() {
        try {
            val snap = db.collection("products").get().await()
            val products = snap.toObjects(Product::class.java)
            productDao.insertProducts(products)
            Log.d(TAG, "✅ منتجات: ${products.size}")
        } catch (e: Exception) { Log.e(TAG, "❌ منتجات", e) }
    }

    private suspend fun syncSales() {
        try {
            val snap = db.collection("sales").get().await()
            val sales = snap.toObjects(Sale::class.java)
            sales.forEach { saleDao.insertSale(it) }
            Log.d(TAG, "✅ مبيعات: ${sales.size}")
        } catch (e: Exception) { Log.e(TAG, "❌ مبيعات", e) }
    }

    private suspend fun syncCustomers() {
        try {
            val snap = db.collection("customers").get().await()
            val customers = snap.toObjects(Customer::class.java)
            customers.forEach { customerDao.insertCustomer(it) }
            Log.d(TAG, "✅ عملاء: ${customers.size}")
        } catch (e: Exception) { Log.e(TAG, "❌ عملاء", e) }
    }

    private suspend fun syncPurchases() {
        try {
            val snap = db.collection("purchases").get().await()
            val purchases = snap.toObjects(Purchase::class.java)
            purchases.forEach { purchaseDao.insertPurchase(it) }
            Log.d(TAG, "✅ مشتريات: ${purchases.size}")
        } catch (e: Exception) { Log.e(TAG, "❌ مشتريات", e) }
    }

    private suspend fun syncExpenses() {
        try {
            val snap = db.collection("expenses").get().await()
            val expenses = snap.toObjects(Expense::class.java)
            expenses.forEach { expenseDao.insertExpense(it) }
            Log.d(TAG, "✅ مصاريف: ${expenses.size}")
        } catch (e: Exception) { Log.e(TAG, "❌ مصاريف", e) }
    }
}
