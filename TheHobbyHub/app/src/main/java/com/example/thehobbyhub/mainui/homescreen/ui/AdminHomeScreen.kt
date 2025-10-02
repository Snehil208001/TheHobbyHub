// com/example/thehobbyhub/mainui/homescreen/ui/AdminHomeScreen.kt
package com.example.thehobbyhub.mainui.homescreen.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.thehobbyhub.mainui.homescreen.viewmodel.*
import com.example.thehobbyhub.ui.theme.Purple6C63FF
import com.example.thehobbyhub.ui.theme.RedFF4B4B

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminHomeScreen(
    navController: NavController,
    viewModel: AdminHomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Admin Dashboard") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.addEvent() }) {
                Icon(Icons.Default.Add, contentDescription = "Add New Event")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { WelcomeSection(adminName = uiState.adminName, city = uiState.city) }
            item { QuickStatsCard(stats = uiState.stats) }
            item { QuickActionsCard(
                onAddEvent = viewModel::addEvent,
                onAddHub = viewModel::addHub,
                onBroadcast = viewModel::broadcastAnnouncement
            ) }
            item { AnalyticsCard(analytics = uiState.analytics) }
            item { InteractiveApprovalsCard(
                requests = uiState.approvalRequests,
                onApprove = viewModel::approveRequest,
                onReject = viewModel::rejectRequest
            ) }
            item { EnhancedEventsCard(events = uiState.managedEvents) }
            item { UserManagementCard(
                recentUsers = uiState.recentUsers,
                onViewAll = viewModel::viewAllUsers
            ) }
            item { AnnouncementsCard(announcements = uiState.pastAnnouncements) }
            item { RecentActivityCard(activities = uiState.activityFeed) }
        }
    }
}


// --- REBUILT AND NEW COMPOSABLE CARDS ---

@Composable
fun WelcomeSection(adminName: String, city: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Welcome, $adminName!", style = MaterialTheme.typography.headlineMedium)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Managing: $city", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
            Icon(Icons.Default.LocationOn, contentDescription = "Location", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(start = 4.dp).size(20.dp))
        }
    }
}

@Composable
fun QuickStatsCard(stats: AdminStats) {
    Card(elevation = CardDefaults.cardElevation(4.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            StatItem(icon = Icons.Default.Group, value = stats.activeUsers.toString(), label = "Active Users")
            StatItem(icon = Icons.Default.Event, value = stats.upcomingEvents.toString(), label = "Upcoming")
            StatItem(icon = Icons.Default.Notifications, value = stats.pendingApprovals.toString(), label = "Approvals", isUrgent = true)
        }
    }
}

@Composable
fun StatItem(icon: ImageVector, value: String, label: String, isUrgent: Boolean = false) {
    // FIXED: Safely convert string to int to prevent crashes
    val intValue = value.toIntOrNull() ?: 0
    val contentColor = if (isUrgent && intValue > 0) RedFF4B4B else MaterialTheme.colorScheme.onSurface
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(icon, contentDescription = label, modifier = Modifier.size(28.dp), tint = contentColor)
        Spacer(modifier = Modifier.height(4.dp))
        Text(value, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = contentColor)
        Text(label, style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
fun QuickActionsCard(onAddEvent: () -> Unit, onAddHub: () -> Unit, onBroadcast: () -> Unit) {
    DashboardCard(title = "Quick Actions", icon = Icons.Default.Bolt) {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = onAddEvent, modifier = Modifier.weight(1f)) { Text("Add Event") }
            OutlinedButton(onClick = onAddHub, modifier = Modifier.weight(1f)) { Text("Add Hub") }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = onBroadcast,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
        ) {
            Icon(Icons.Default.Campaign, contentDescription = "Broadcast", modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Broadcast Announcement")
        }
    }
}

@Composable
fun AnalyticsCard(analytics: AnalyticsData) {
    DashboardCard(title = "Analytics & Insights", icon = Icons.Default.BarChart) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Events This Month", style = MaterialTheme.typography.labelMedium)
                Text(analytics.eventsThisMonth.toString(), style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.ExtraBold, color = Purple6C63FF)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("User Growth", style = MaterialTheme.typography.labelMedium)
                Text("+${analytics.userGrowthPercent}%", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.ExtraBold, color = Purple6C63FF)
            }
        }
        Text(
            "Charts and graphs would be displayed here.",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun InteractiveApprovalsCard(requests: List<ApprovalRequest>, onApprove: (String) -> Unit, onReject: (String) -> Unit) {
    DashboardCard(title = "Pending Approvals", icon = Icons.Default.Task) {
        if (requests.isEmpty()) {
            Text("No pending requests.", style = MaterialTheme.typography.bodyMedium)
        } else {
            requests.forEach { request ->
                Column {
                    val (icon, color) = when (request.type) {
                        ApprovalType.HUB_REQUEST -> Icons.Default.Store to MaterialTheme.colorScheme.primary
                        ApprovalType.EVENT_SUBMISSION -> Icons.Default.Event to MaterialTheme.colorScheme.secondary
                        ApprovalType.USER_REPORT -> Icons.Default.Flag to RedFF4B4B
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(icon, contentDescription = request.type.name, tint = color)
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(request.description, fontWeight = FontWeight.SemiBold)
                            if (request.reportedBy != null) {
                                Text("Reported by: ${request.reportedBy}", style = MaterialTheme.typography.labelSmall)
                            }
                        }
                    }
                    Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp), horizontalArrangement = Arrangement.End) {
                        Button(onClick = { onApprove(request.id) }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)), modifier = Modifier.height(36.dp)) { Text("Approve") }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = { onReject(request.id) }, colors = ButtonDefaults.buttonColors(containerColor = RedFF4B4B), modifier = Modifier.height(36.dp)) { Text("Reject") }
                    }
                }
                Divider(modifier = Modifier.padding(vertical = 8.dp))
            }
        }
    }
}

@Composable
fun EnhancedEventsCard(events: List<ManagedEvent>) {
    DashboardCard(title = "Event Management", icon = Icons.Default.EditCalendar) {
        events.forEach { event ->
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 8.dp)) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(event.title, fontWeight = FontWeight.Bold)
                    Text("${event.hubName} • ${event.date}", style = MaterialTheme.typography.bodySmall)
                    EventStatusBadge(status = event.status)
                }
                Row {
                    IconButton(onClick = { /* Edit */ }) { Icon(Icons.Default.Edit, "Edit") }
                    IconButton(onClick = { /* View */ }) { Icon(Icons.Default.People, "View Participants") }
                }
            }
            Divider()
        }
    }
}

@Composable
fun EventStatusBadge(status: EventStatus) {
    val (text, color) = when (status) {
        EventStatus.UPCOMING -> "Upcoming" to Color(0xFF4CAF50)
        EventStatus.ONGOING -> "Ongoing" to Color(0xFFFFC107)
        EventStatus.COMPLETED -> "Completed" to Color.Gray
    }
    Text(
        text,
        color = Color.White,
        fontSize = 10.sp,
        modifier = Modifier
            .padding(top = 4.dp)
            .clip(CircleShape)
            .background(color)
            .padding(horizontal = 8.dp, vertical = 2.dp)
    )
}

@Composable
fun UserManagementCard(recentUsers: List<RecentUser>, onViewAll: () -> Unit) {
    DashboardCard(title = "User Management", icon = Icons.Default.ManageAccounts) {
        Text("Newly Joined Users", fontWeight = FontWeight.Bold)
        recentUsers.forEach { user ->
            Row(modifier = Modifier.padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(user.name, modifier = Modifier.weight(1f))
                Text(user.joinDate, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedButton(onClick = onViewAll, modifier = Modifier.fillMaxWidth()) {
            Text("View All Users")
        }
    }
}

@Composable
fun AnnouncementsCard(announcements: List<Announcement>) {
    DashboardCard(title = "Past Announcements", icon = Icons.Default.History) {
        announcements.forEach { announcement ->
            Row(modifier = Modifier.padding(bottom = 4.dp)) {
                Icon(Icons.Default.Campaign, contentDescription = null, modifier = Modifier.padding(end = 8.dp))
                Column {
                    Text(announcement.message)
                    Text(announcement.timestamp, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                }
            }
            Divider()
        }
    }
}

@Composable
fun RecentActivityCard(activities: List<RecentActivity>) {
    DashboardCard(title = "Recent Activity Feed", icon = Icons.Default.Timeline) {
        activities.forEach { activity ->
            Row(modifier = Modifier.padding(bottom = 4.dp)) {
                Text("•", modifier = Modifier.padding(end = 8.dp))
                Column {
                    Text(activity.description)
                    Text(activity.timestamp, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                }
            }
        }
    }
}


// --- GENERIC DASHBOARD CARD WRAPPER ---
@Composable
fun DashboardCard(
    title: String,
    icon: ImageVector,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(elevation = CardDefaults.cardElevation(4.dp), modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, contentDescription = title, modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(16.dp))
            content()
        }
    }
}