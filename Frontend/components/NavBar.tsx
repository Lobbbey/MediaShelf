'use client';
import Link from 'next/link';

export default function NavBar(){
  return(
  <nav className="fixed top-0 left-0 w-full z-50">
      <div>
        <Link href="/">
          <span> TEST </span>
        </Link>
      </div>
  </nav>
  );
}
