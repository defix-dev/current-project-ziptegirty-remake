export class UserManager {
    static async getUserMetadata() {
        const res = await fetch("/api/v1/user");
        if(res.status !== 200) return null;
        const metadata = await res.json();
        return {
            username: metadata.username,
            id: metadata.id
        };
    }
}