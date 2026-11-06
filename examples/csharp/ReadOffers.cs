// Read Every Offer from Amazon – C# example (.NET 6+, top-level statements)
// Usage: dotnet new console -n Example && copy this file to Program.cs, then ASIN_API_KEY=your-key dotnet run
using System.Text;
using System.Text.Json;

var apiKey = Environment.GetEnvironmentVariable("ASIN_API_KEY") ?? throw new InvalidOperationException("Set ASIN_API_KEY");
using var http = new HttpClient { Timeout = TimeSpan.FromSeconds(120) };
http.DefaultRequestHeaders.Add("Authorization", "Bearer " + apiKey);

var body = new StringContent(@"{""asin"": ""B09DFCB66S"", ""marketplace"": ""com""}", Encoding.UTF8, "application/json");
var request = new HttpRequestMessage(new HttpMethod("POST"), "https://www.asin.dev/api/v1/offers") { Content = body };
var response = await http.SendAsync(request);
if (!response.IsSuccessStatusCode)
{
    Console.Error.WriteLine($"HTTP {(int)response.StatusCode}: {await response.Content.ReadAsStringAsync()}");
    return;
}

using var doc = JsonDocument.Parse(await response.Content.ReadAsStringAsync());
Console.WriteLine(JsonSerializer.Serialize(doc, new JsonSerializerOptions { WriteIndented = true }));
