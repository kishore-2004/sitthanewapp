const API_URL = 'http://localhost:8080/api/v1';

async function testForgotPassword() {
  console.log('\n=== Testing Forgot Password Flow ===\n');

  // Test 1: Request OTP
  console.log('Test 1: Requesting OTP...');
  try {
    const response = await fetch(`${API_URL}/auth/forgot-password`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ email: 'test@example.com' })
    });
    
    const data = await response.json();
    console.log('✓ Response:', data);
    
    if (response.ok) {
      console.log('✓ OTP request successful!');
      console.log('  Check backend console for OTP');
    } else {
      console.log('✗ OTP request failed:', data.error);
    }
  } catch (error) {
    console.log('✗ Error:', error.message);
  }

  console.log('\n---\n');

  // Test 2: Reset Password (you need to get OTP from backend console)
  console.log('Test 2: Reset Password');
  console.log('  Enter the OTP from backend console and run:');
  console.log('  node test-reset-password.js <email> <otp> <newPassword>');
}

// If arguments provided, test reset password
if (process.argv.length === 5) {
  const [,, email, otp, newPassword] = process.argv;
  
  console.log('\n=== Testing Reset Password ===\n');
  console.log(`Email: ${email}`);
  console.log(`OTP: ${otp}`);
  console.log(`New Password: ${newPassword}`);
  
  fetch(`${API_URL}/auth/reset-password`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ email, otp, newPassword })
  })
  .then(res => res.json())
  .then(data => {
    console.log('\n✓ Response:', data);
    if (data.message) {
      console.log('✓ Password reset successful!');
    } else {
      console.log('✗ Password reset failed:', data.error);
    }
  })
  .catch(error => {
    console.log('✗ Error:', error.message);
  });
} else {
  testForgotPassword();
}
