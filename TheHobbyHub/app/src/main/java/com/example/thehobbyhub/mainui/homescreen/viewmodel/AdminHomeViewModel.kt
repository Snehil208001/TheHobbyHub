// com/example/thehobbyhub/mainui/homescreen/viewmodel/AdminHomeViewModel.kt
package com.example.thehobbyhub.mainui.homescreen.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.thehobbyhub.core.navigations.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// --- NEW AND EXPANDED DATA MODELS ---

// For Quick Stats Card
data class AdminStats(
    val activeUsers: Int,
    val upcomingEvents: Int,
    val pendingApprovals: Int
)

// For Analytics Card
data class AnalyticsData(
    val eventsThisMonth: Int = 18,
    val userGrowthPercent: Int = 12
)

// For Event Management Card
enum class EventStatus { UPCOMING, ONGOING, COMPLETED }
data class ManagedEvent(
    val id: String,
    val title: String,
    val date: String,
    val participantCount: Int,
    val status: EventStatus,
    val hubName: String
)

// For User Management Card
data class RecentUser(
    val name: String,
    val joinDate: String
)

// For Approvals Card
enum class ApprovalType { HUB_REQUEST, EVENT_SUBMISSION, USER_REPORT }
data class ApprovalRequest(
    val id: String,
    val description: String,
    val type: ApprovalType,
    val reportedBy: String? = null
)

// For Announcements Card
data class Announcement(
    val message: String,
    val timestamp: String = SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault()).format(Date())
)

// For Activity Feed Card
data class RecentActivity(
    val description: String,
    val timestamp: String = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
)


// --- Main UI State ---
data class AdminHomeUiState(
    val adminName: String = "Snehil",
    val city: String = "Patna",
    val stats: AdminStats = AdminStats(0, 0, 0),
    val analytics: AnalyticsData = AnalyticsData(),
    val managedEvents: List<ManagedEvent> = emptyList(),
    val recentUsers: List<RecentUser> = emptyList(),
    val approvalRequests: List<ApprovalRequest> = emptyList(),
    val pastAnnouncements: List<Announcement> = emptyList(),
    val activityFeed: List<RecentActivity> = emptyList()
)

@HiltViewModel
class AdminHomeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(AdminHomeUiState())
    val uiState: StateFlow<AdminHomeUiState> = _uiState.asStateFlow()

    init {
        val city = savedStateHandle.get<String>(Screen.AdminHome.ARG_CITY) ?: "Unknown City"
        loadAdminData(city)
    }

    private fun loadAdminData(city: String) {
        // In a real app, this data would be fetched from a repository
        _uiState.value = AdminHomeUiState(
            adminName = "Snehil",
            city = city,
            stats = AdminStats(activeUsers = 125, upcomingEvents = 8, pendingApprovals = 3),
            analytics = AnalyticsData(eventsThisMonth = 18, userGrowthPercent = 12),
            managedEvents = listOf(
                ManagedEvent("e1", "Weekend Pottery Workshop", "Oct 4", 15, EventStatus.UPCOMING, "Patna Potters"),
                ManagedEvent("e2", "Intro to Kotlin for Beginners", "Oct 12", 28, EventStatus.UPCOMING, "Patna Coders"),
                ManagedEvent("e3", "Sunrise Photo Walk", "Oct 11", 22, EventStatus.ONGOING, "Patna Shutterbugs"),
                ManagedEvent("e4", "Music Fest 2025", "Sep 28", 150, EventStatus.COMPLETED, "Junction Events")
            ),
            recentUsers = listOf(
                RecentUser("Ravi Kumar", "Joined 2 days ago"),
                RecentUser("Priya Sharma", "Joined 5 days ago"),
                RecentUser("Amit Singh", "Joined 1 week ago")
            ),
            approvalRequests = listOf(
                ApprovalRequest("r1", "New Hub: 'Patna Painters'", ApprovalType.HUB_REQUEST),
                ApprovalRequest("r2", "Event: 'Open Mic Night'", ApprovalType.EVENT_SUBMISSION),
                ApprovalRequest("r3", "Spam in 'Board Games Club'", ApprovalType.USER_REPORT, "Anjali")
            ),
            pastAnnouncements = listOf(
                Announcement("Reminder: Photography workshop this Sunday!"),
                Announcement("New 'Cycling Club' hub has been approved.")
            ),
            activityFeed = listOf(
                RecentActivity("Event 'Weekend Pottery Workshop' reached 15 participants."),
                RecentActivity("User 'Ravi Kumar' joined the 'Patna Runners' hub."),
                RecentActivity("New post in 'Foodies of Patna' hub.")
            )
        )
    }

    // --- Placeholder functions for new actions ---
    fun addEvent() { /* TODO: Navigate to create event screen */ }
    fun addHub() { /* TODO: Navigate to create hub screen */ }
    fun broadcastAnnouncement() { /* TODO: Show announcement dialog */ }
    fun approveRequest(requestId: String) { /* TODO: Handle approval logic */ }
    fun rejectRequest(requestId: String) { /* TODO: Handle rejection logic */ }
    fun viewAllUsers() { /* TODO: Navigate to user list screen */ }
}