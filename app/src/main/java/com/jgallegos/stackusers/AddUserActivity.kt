package com.jgallegos.stackusers

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity

class AddUserActivity : AppCompatActivity() {

    enum class ErrorCode {
        ERR_NO_ERROR,
        ERR_BLANK_DISPLAYNAME,
        ERR_REPUTATION_NOT_ODD,
        ERR_BRONZE_NOT_MUL_3,
        ERR_SILVER_NOT_MUL_3,
        ERR_GOLD_NOT_MUL_3,
        ERR_UNKNOWN
    }

    private lateinit var btnAddUser: Button
    private lateinit var txtDisplayName: EditText
    private lateinit var txtReputation: EditText
    private lateinit var txtGoldBadgeCount: EditText
    private lateinit var txtSilverBadgeCount: EditText
    private lateinit var txtBronzeBadgeCount: EditText
    private lateinit var svAddUserFieldContatiner: ScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setup UI
        setContentView(R.layout.activity_add_user)
        btnAddUser = findViewById(R.id.btnAddUser)
        txtDisplayName = findViewById(R.id.txtDisplayName)
        txtReputation = findViewById(R.id.txtReputation)
        txtGoldBadgeCount = findViewById(R.id.txtGoldBadgeCount)
        txtSilverBadgeCount = findViewById(R.id.txtSilverBadgeCount)
        txtBronzeBadgeCount = findViewById(R.id.txtBronzeBadgeCount)
        svAddUserFieldContatiner = findViewById(R.id.svAddUserFieldContatiner)

        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Add a back button to the title bar

        // Set our Add User button onClickListener
        btnAddUser.setOnClickListener { addUserClick() }
    }

    /**
     * ActionBar processing, used for when the user clicks the back button.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.getItemId()) {
            android.R.id.home -> { // Back button in ActionBar clicked
                // Tell previous screen that we have not created a user and close activity
                setResult(RESULT_CANCELED, null)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Runs when user clicks "Add User"
     * Validates the fields and sends information back to previous activity if valid.
     */
    fun addUserClick() {
        // Get values
        var displayName = txtDisplayName.text.toString()
        var reputation = txtReputation.text.toString().toIntOrNull()?:0
        var goldBadgeCount = txtGoldBadgeCount.text.toString().toIntOrNull()?:0
        var silverBadgeCount = txtSilverBadgeCount.text.toString().toIntOrNull()?:0
        var bronzeBadgeCount = txtBronzeBadgeCount.text.toString().toIntOrNull()?:0

        // Run form validation
        val errCode = checkErrors(displayName, bronzeBadgeCount, silverBadgeCount, goldBadgeCount, reputation)
        if(errCode == ErrorCode.ERR_NO_ERROR) {
            // Put values into an Intent and close form
            var intent = Intent()
            intent.putExtra("KEY_DISPLAYNAME", displayName)
            intent.putExtra("KEY_REPUTATION", reputation)
            intent.putExtra("KEY_GBCOUNT",goldBadgeCount)
            intent.putExtra("KEY_SBCOUNT", silverBadgeCount)
            intent.putExtra("KEY_BBCOUNT", bronzeBadgeCount)

            setResult(RESULT_OK, intent)
            finish()
        } else {
            // Something was wrong, present error to user
            handleFieldError(errCode)
        }
    }

    /**
     * Verifies that the parameters are met to standard.
     */
    fun checkErrors(displayName : String, bronzeBadgeCount : Int, silverBadgeCount : Int, goldBadgeCount : Int, reputation : Int) : ErrorCode {
        // Check Display Name isn't blank
        if (displayName.length == 0) { return ErrorCode.ERR_BLANK_DISPLAYNAME }

        // Check reputation is odd
        if (reputation % 2 == 0) { return ErrorCode.ERR_REPUTATION_NOT_ODD }

        // Check badge counts are a multiple of 3
        if (goldBadgeCount % 3 > 0) { return ErrorCode.ERR_GOLD_NOT_MUL_3 }
        if (silverBadgeCount % 3 > 0) {  return ErrorCode.ERR_SILVER_NOT_MUL_3 }
        if (bronzeBadgeCount % 3 > 0) { return ErrorCode.ERR_BRONZE_NOT_MUL_3 }

        // No error
        return ErrorCode.ERR_NO_ERROR
        }

    /**
     * Determines which field has the error and what message the user should be presented with.
     */
    fun handleFieldError(err : ErrorCode) {
        // Generate error message
        var errMessage: String
        var errView: EditText?

        // Determine what we need to tell the user, and which field causes the issue
        when (err) {
            ErrorCode.ERR_BLANK_DISPLAYNAME -> {
                errMessage = "Display Name cannot be blank"
                errView = txtDisplayName
            }

            ErrorCode.ERR_REPUTATION_NOT_ODD -> {
                errMessage = "Reputation must be an odd number"
                errView = txtReputation
            }

            ErrorCode.ERR_GOLD_NOT_MUL_3 -> {
                errMessage = "Gold badges must be a multiple of 3"
                errView = txtGoldBadgeCount
            }

            ErrorCode.ERR_SILVER_NOT_MUL_3 -> {
                errMessage = "Silver badges must be a multiple of 3"
                errView = txtSilverBadgeCount
            }

            ErrorCode.ERR_BRONZE_NOT_MUL_3 -> {
                errMessage = "Bronze badges must be a multiple of 3"
                errView = txtBronzeBadgeCount
            }
            else -> return
        }

        // Show the offending field with a description
        presentError(errView, errMessage)
    }

    /**
     * Shows an error message on the erroneous field.
     */
    fun presentError(errView : EditText, errMessage : String) {
        // Show error message on the view
        errView.setError(errMessage)

        // Scroll to the view with the error
        svAddUserFieldContatiner.scrollToDescendant(errView)
    }
}